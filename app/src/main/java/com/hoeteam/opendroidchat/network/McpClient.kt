/*
OpenDroidChat MCP Client Module
Copyright (C) 2025-2026 HOE Team. All rights reserved.
The source code is open-sourced under the MIT License.
*/
package com.hoeteam.opendroidchat.network

import com.hoeteam.opendroidchat.data.McpServer
import com.hoeteam.opendroidchat.data.McpTool
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*

/**
 * 一次模型希望执行的 MCP 工具调用（由模型输出中解析而来）。
 */
data class McpToolCall(
    val server: String,
    val tool: String,
    val arguments: Map<String, JsonElement>
)

/**
 * MCP (Model Context Protocol) 客户端。
 * 使用 JSON-RPC 2.0 over HTTP 与 MCP 服务器通信，支持 tools/list 与 tools/call。
 */
class McpClient {

    private val json = Json { ignoreUnknownKeys = true }

    private val client = HttpClient(Android) {
        install(HttpTimeout) {
            requestTimeoutMillis = 60000
            connectTimeoutMillis = 15000
        }
        expectSuccess = false
    }

    /**
     * 获取服务器暴露的工具列表。失败时返回空列表（调用方据此跳过该服务器）。
     */
    suspend fun listTools(server: McpServer): List<McpTool> {
        val requestBody = buildJsonObject {
            put("jsonrpc", "2.0")
            put("id", 1)
            put("method", "tools/list")
        }

        val response: JsonElement = try {
            post(server, requestBody)
        } catch (e: Exception) {
            return emptyList()
        }

        val result = response.jsonObject["result"] as? JsonObject ?: return emptyList()
        val tools = result["tools"] as? JsonArray ?: return emptyList()

        return tools.mapNotNull { el ->
            val obj = el as? JsonObject ?: return@mapNotNull null
            val name = obj["name"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
            McpTool(
                name = name,
                description = obj["description"]?.jsonPrimitive?.contentOrNull ?: "",
                inputSchema = obj["inputSchema"]?.toString()
            )
        }
    }

    /**
     * 调用服务器上的某个工具，返回文本结果。
     */
    suspend fun callTool(server: McpServer, toolName: String, arguments: Map<String, JsonElement>): String {
        val requestBody = buildJsonObject {
            put("jsonrpc", "2.0")
            put("id", 1)
            put("method", "tools/call")
            put("params", buildJsonObject {
                put("name", toolName)
                put("arguments", buildJsonObject {
                    arguments.forEach { (key, value) -> put(key, value) }
                })
            })
        }

        val response: JsonElement = try {
            post(server, requestBody)
        } catch (e: Exception) {
            return "调用工具失败：${e.message ?: e::class.simpleName}"
        }

        return extractTextContent(response)
    }

    private suspend fun post(server: McpServer, body: JsonObject): JsonElement {
        val response: HttpResponse = client.post(server.url) {
            contentType(ContentType.Application.Json)
            // 若配置了 API Key，则按 MCP 标准约定通过 Authorization: Bearer 传递
            if (server.apiKey.isNotBlank()) {
                header(HttpHeaders.Authorization, "Bearer ${server.apiKey.trim()}")
            }
            setBody(body.toString())
        }
        val bodyText = response.bodyAsText()
        return try {
            json.parseToJsonElement(bodyText)
        } catch (e: Exception) {
            // 非 JSON 响应，原样包裹为文本
            buildJsonObject {
                put("result", buildJsonObject {
                    put("content", buildJsonArray {
                        add(buildJsonObject {
                            put("type", "text")
                            put("text", bodyText)
                        })
                    })
                })
            }
        }
    }

    /**
     * 从 MCP 响应中提取文本内容（兼容 result.content[].text 标准结构）。
     */
    private fun extractTextContent(response: JsonElement): String {
        val result = response.jsonObject["result"] as? JsonObject ?: return response.toString()
        val content = result["content"] as? JsonArray
            ?: return result["structuredContent"]?.toString() ?: result.toString()

        val sb = StringBuilder()
        content.forEach { el ->
            val obj = el as? JsonObject ?: return@forEach
            val type = obj["type"]?.jsonPrimitive?.contentOrNull
            when (type) {
                "text" -> {
                    obj["text"]?.jsonPrimitive?.contentOrNull?.let { sb.append(it) }
                }
                else -> sb.append(el.toString())
            }
            sb.append("\n")
        }
        return sb.toString().trim()
    }

    companion object {
        private val parseJson = Json { ignoreUnknownKeys = true }

        /**
         * 从模型输出中提取所有 "[MCP_CALL]{...}" 完整块（含前缀）。
         * 使用平衡花括号扫描器，支持任意嵌套（对象/数组/转义字符串），
         * 因此不会像正则那样在嵌套 JSON 处被截断。
         */
        fun extractMcpCallBlocks(text: String): List<String> {
            val blocks = mutableListOf<String>()
            var searchFrom = 0
            while (true) {
                val start = text.indexOf("[MCP_CALL]", searchFrom)
                if (start < 0) break
                // 跳过前缀后的空白
                var braceStart = start + "[MCP_CALL]".length
                while (braceStart < text.length && text[braceStart].isWhitespace()) braceStart++
                if (braceStart >= text.length || text[braceStart] != '{') {
                    searchFrom = start + 1
                    continue
                }
                // 扫描平衡花括号，正确处理字符串与转义
                var depth = 0
                var inString = false
                var escaped = false
                var end = -1
                for (j in braceStart until text.length) {
                    val c = text[j]
                    if (inString) {
                        if (escaped) escaped = false
                        else if (c == '\\') escaped = true
                        else if (c == '"') inString = false
                    } else {
                        when (c) {
                            '"' -> inString = true
                            '{' -> depth++
                            '}' -> {
                                depth--
                                if (depth == 0) { end = j; break }
                            }
                        }
                    }
                }
                if (end < 0) { searchFrom = start + 1; continue }
                blocks += text.substring(start, end + 1)
                searchFrom = end + 1
            }
            return blocks
        }

        /**
         * 从模型输出中解析出 [McpToolCall] 列表。
         * 约定：模型需输出形如
         *   [MCP_CALL]{"server":"...","tool":"...","arguments":{...}}
         * 的独立行（arguments 支持任意嵌套）。
         */
        fun parseMcpCalls(text: String): List<McpToolCall> {
            val result = mutableListOf<McpToolCall>()
            for (block in extractMcpCallBlocks(text)) {
                val jsonText = block.substringAfter("[MCP_CALL]").trim()
                try {
                    val json = parseJson.parseToJsonElement(jsonText).jsonObject
                    val server = json["server"]?.jsonPrimitive?.contentOrNull ?: continue
                    val tool = json["tool"]?.jsonPrimitive?.contentOrNull ?: continue
                    val args = (json["arguments"] as? JsonObject)?.let { obj ->
                        obj.mapValues { it.value }
                    } ?: emptyMap()
                    result += McpToolCall(server = server, tool = tool, arguments = args)
                } catch (e: Exception) {
                    // 忽略解析失败的标记
                }
            }
            return result
        }

        /**
         * 从模型输出中移除所有 [MCP_CALL] 标记，仅保留纯文本。
         */
        fun stripMcpCalls(text: String): String {
            var out = text
            for (block in extractMcpCallBlocks(text)) {
                out = out.replace(block, "")
            }
            return out.trim()
        }

        /**
         * 流式场景下，返回文本尾部需要"暂存"的字符数：
         * 这些字符可能是 [MCP_CALL] 标记被分块时的前缀，需等待下一块确认。
         */
        fun holdBackLength(text: String): Int {
            val prefix = "[MCP_CALL]"
            for (len in prefix.length downTo 1) {
                if (len <= text.length && text.endsWith(prefix.substring(0, len))) {
                    return len
                }
            }
            return 0
        }
    }
}
