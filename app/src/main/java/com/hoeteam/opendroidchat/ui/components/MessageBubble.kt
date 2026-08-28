/*
OpenDroidChat Message Bubble Component
Copyright (C) 2025-2026 HOE Team. All rights reserved.
The source code is open-sourced under the MIT License.
*/
package com.hoeteam.opendroidchat.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hoeteam.opendroidchat.data.McpCallInfo
import com.hoeteam.opendroidchat.data.Message
import com.hoeteam.opendroidchat.data.Sender
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 复制状态枚举
enum class CopyState {
    Idle,      // 未复制
    Copied     // 已复制
}

// 复制到剪贴板的辅助函数
private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("聊天内容", text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, context.getString(com.hoeteam.opendroidchat.R.string.message_copied_to_clipboard), Toast.LENGTH_SHORT).show()
}

@Composable
fun MessageBubble(
    message: Message,
    hybridMarkdown: @Composable (String, Modifier, Color) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var copyState by remember { mutableStateOf(CopyState.Idle) }

    val isUser = message.sender == Sender.USER
    val bubbleColor = when {
        isUser -> MaterialTheme.colorScheme.primary
        message.text.contains("LLM 响应出错") -> MaterialTheme.colorScheme.errorContainer
        else -> MaterialTheme.colorScheme.surfaceContainerHigh
    }
    val textColor = when {
        isUser -> MaterialTheme.colorScheme.onPrimary
        message.text.contains("LLM 响应出错") -> MaterialTheme.colorScheme.onErrorContainer
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    // M3E 标志性非对称大气圆角
    val bubbleShape = if (isUser) {
        RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 24.dp, bottomEnd = 4.dp)
    } else {
        RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 24.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = if (isUser) 60.dp else 8.dp,
                end = if (isUser) 8.dp else 60.dp,
                top = 4.dp,
                bottom = 4.dp
            ),
        contentAlignment = if (isUser) Alignment.TopEnd else Alignment.TopStart
    ) {
        Column(
            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
        ) {
            // 消息气泡 - 宽度自适应内容
            Surface(
                color = bubbleColor,
                shape = bubbleShape,
                tonalElevation = if (isUser) 0.dp else 2.dp,
                shadowElevation = 1.dp
            ) {
                if (isUser) {
                    Column(
                        modifier = Modifier.widthIn(min = 60.dp)
                    ) {
                        if (message.text.isNotBlank()) {
                            Text(
                                text = message.text,
                                color = textColor,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        // 用户消息底部显示文件 Chip（如果有）
                        if (message.selectedFile != null) {
                            FileInfoChip(
                                fileName = message.selectedFile.fileName,
                                isCodeFile = message.selectedFile.isCodeFile,
                                modifier = Modifier.padding(start = 12.dp, bottom = 8.dp, end = 12.dp)
                            )
                        }
                    }
                } else {
                    Column(modifier = Modifier.widthIn(min = 60.dp)) {
                        // 思考内容展示（如果有）
                        if (!message.reasoningText.isNullOrBlank()) {
                            ThinkingBlock(
                                reasoningText = message.reasoningText,
                                isStreaming = message.isThinkingStreaming
                            )
                        }

                        // MCP 工具调用记录（如果有）
                        if (message.mcpCalls.isNotEmpty()) {
                            McpCallsBlock(calls = message.mcpCalls)
                        }


                        // 常规内容
                        if (message.text.isNotBlank()) {
                            hybridMarkdown(
                                message.text,
                                Modifier.padding(
                                    horizontal = 16.dp,
                                    vertical = if (message.reasoningText.isNullOrBlank()) 12.dp else 8.dp
                                ),
                                textColor
                            )
                        }
                    }
                }
            }

            // 复制按钮的 M3E 物理反馈
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val buttonScale by animateFloatAsState(
                targetValue = if (isPressed) 0.85f else 1f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
                label = "CopyButtonScale"
            )

            IconButton(
                onClick = {
                    scope.launch {
                        copyToClipboard(context, message.text)
                        copyState = CopyState.Copied
                        delay(2000)
                        copyState = CopyState.Idle
                    }
                },
                modifier = Modifier
                    .padding(top = 2.dp)
                    .size(36.dp)
                    .scale(buttonScale),
                interactionSource = interactionSource
            ) {
                Icon(
                    imageVector = when (copyState) {
                        CopyState.Idle -> Icons.Default.ContentCopy
                        CopyState.Copied -> Icons.Default.DoneAll
                    },
                    contentDescription = null,
                    tint = if (copyState == CopyState.Copied) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun ThinkingBlock(
    reasoningText: String,
    isStreaming: Boolean
) {
    var expanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // 标题栏 - 可折叠
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isStreaming) stringResource(com.hoeteam.opendroidchat.R.string.message_thinking_in_progress) else stringResource(com.hoeteam.opendroidchat.R.string.message_thinking_complete),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.tertiary
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.size(18.dp)
            )
        }

        // 思考内容
        AnimatedVisibility(visible = expanded) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = reasoningText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun FileInfoChip(
    fileName: String,
    isCodeFile: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)),
        modifier = modifier.height(28.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 6.dp, end = 6.dp)
        ) {
            Icon(
                imageVector = if (isCodeFile) Icons.Filled.Code else Icons.Filled.Description,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = fileName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun McpCallsBlock(calls: List<McpCallInfo>) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(true) }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            // 标题栏 - 可折叠
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = context.getString(
                        com.hoeteam.opendroidchat.R.string.message_mcp_tools_title,
                        calls.size
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.size(18.dp)
                )
            }

            // 调用详情
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    calls.forEach { call ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (call.pending) {
                                // 调用进行中：显示加载转圈
                                CircularProgressIndicator(
                                    modifier = Modifier.size(14.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = context.getString(
                                        com.hoeteam.opendroidchat.R.string.message_mcp_loading,
                                        call.serverName,
                                        call.toolName
                                    ),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f)
                                )
                            } else if (call.error != null) {
                                Icon(
                                    Icons.Filled.Warning,
                                    null,
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${call.toolName} · ${call.serverName}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                    Text(
                                        text = call.error,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.error.copy(alpha = 0.85f)
                                    )
                                }
                            } else {
                                Text(
                                    text = context.getString(
                                        com.hoeteam.opendroidchat.R.string.message_mcp_call_item,
                                        call.serverName,
                                        call.toolName,
                                        call.durationMs
                                    ),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

