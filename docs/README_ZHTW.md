<h1 align="center">OpenDroidChat</h1>

<div align="center">
    <img width="150" src="../images/odc-logo.png" alt="專案Logo">
</div>

<h3 align="center">一款為 Android 6+ 設計的開源、輕量級 LLM API 聊天客戶端</h3>

<p align="center"><s>哇還有API額度浪費裝置</s></p>

<div align="center">
  
[![Stars](https://img.shields.io/github/stars/HOE-Team/OpenDroidChat?style=for-the-badge&logo=data:image/svg%2bxml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEiIHdpZHRoPSIxNiIgaGVpZ2h0PSIxNiI+PHBhdGggZD0iTTggLjI1YS43NS43NSAwIDAgMSAuNjczLjQxOGwxLjg4MiAzLjgxNSA0LjIxLjYxMmEuNzUuNzUgMCAwIDEgLjQxNiAxLjI3OWwtMy4wNDYgMi45Ny43MTkgNC4xOTJhLjc1MS43NTEgMCAwIDEtMS4wODguNzkxTDggMTIuMzQ3bC0zLjc2NiAxLjk4YS43NS43NSAwIDAgMS0xLjA4OC0uNzlsLjcyLTQuMTk0TC44MTggNi4zNzRhLjc1Ljc1IDAgMCAxIC40MTYtMS4yOGw0LjIxLS42MTFMNy4zMjcuNjY4QS43NS43NSAwIDAgMSA4IC4yNVoiIGZpbGw9IiNlYWM1NGYiLz48L3N2Zz4=&logoSize=auto&label=Stars&labelColor=444444&color=eac54f)](https://github.com/HOE-Team/OpenDroidChat)
[![LICENSE](https://img.shields.io/github/license/HOE-Team/OpenDroidChat?style=for-the-badge)](https://github.com/HOE-Team/OpenDroidChat/blob/main/LICENSE)
![GitHub Release](https://img.shields.io/github/v/release/HOE-Team/OpenDroidChat?label=Release&logo=github&style=for-the-badge)

**[简体中文](../README.md) | [English](README_EN.md) | 繁體中文**
</div>

> [!IMPORTANT]
> OpenDroidChat正處於危機！訪問並了解 [KeepAndroidOpen[↗]](https://keepandroidopen.org)，反對 Google 強制驗證所有 Android 開發者的政策 —— 這破壞了 Android 的開放根基

> [!NOTE]
> 程式翻譯尚不完善，如果您有能力進行 OpenDroidChat 專案的本土化，歡迎向我們發送 [Pull Request](https://github.com/HOE-Team/OpenDroidChat/pulls)。

## 🔨 技術棧

* [**Kotlin**[↗]](https://kotlinlang.org/) — 主要程式語言
* [**Jetpack Compose**[↗]](https://developer.android.com/jetpack/compose) — 宣告式 UI 框架
* [**Material Design 3 (M3)**[↗]](https://m3.material.io/) — 原生 GUI 設計實現
* [**Gradle**[↗]](https://gradle.org/) — 建構工具
* [**Markwon**[↗]](https://github.com/noties/Markwon) — 用於渲染 LLM 訊息中的 Markdown
* [**OkHttp**[↗]](https://square.ac.cn/okhttp/) — 高效、功能強大的 Java/Android HTTP 用戶端。
* [**Ktor**[↗]](https://ktor.io/) — 基於 Kotlin 協程的非同步後端框架，支援多平台。

## ✨ 特性

* 支援 Android 6.0(API等級23) 及以上版本
* 系統原生、美觀的 Material 3 Expressive 介面
* **支援 Markdown 訊息渲染**（程式碼區塊、標題、連結等）
* 輕量級，易於空間拮据的裝置使用
* 可接入多種 LLM API
* 使用加密，保護使用者LLM API Key
* 可啟用模型思考，增加AI輸出內容準確性

> [!NOTE]
>
> ### 預置的 LLM 提供商
>
> * [x] OpenAI / Azure OpenAI
> * [x] Google Gemini
> * [x] DeepSeek
> * [x] Dashscope (阿里雲百煉)
> * [x] Claude 

## 📥 安裝

1. 從 [Releases[↗]](https://github.com/HOE-Team/OpenDroidChat/releases) 下載APK 檔案
2. (建議) 校驗安裝包完整性
3. 使用軟體安裝器安裝

## 🛠️ 從原始碼建構

確保已安裝 **Android Studio** 或 **Gradle**。

> [!IMPORTANT]
>
> * 你可能需要使用 VPN 或代理來下載 Gradle 使用的外部資源。
> * 你可能需要一台效能足夠的電腦來完成編譯。

### 🔧 基本建構（Debug 版本）

```bash
# 克隆倉庫
git clone https://github.com/HOE-Team/OpenDroidChat.git
cd OpenDroidChat

# 使用 Gradle Wrapper 建構 Debug 版本
./gradlew assembleDebug    # Linux / macOS
gradlew.bat assembleDebug   # Windows
```

建構成功後，APK 位於 `app/build/outputs/apk/`。

**如果要執行Release建構，請參閱 [Release建構指南[↗]](/docs/PERFORM_A_RELEASE_BUILD.md)**


## 📁 專案結構

```
project/
├─ app/                  # 原始碼
├─ docs/                 # 文件
├─ gradle/               # Gradle 配置
├─ build.gradle.kts      # 專案建構配置
├─ settings.gradle.kts   # Gradle 設定
├─ gradlew               # Gradle Wrapper (Linux / macOS)
├─ gradlew.bat           # Gradle Wrapper (Windows)
├─ LICENSE               # 許可證
├─ CODE_OF_CONDUCT.md    # 貢獻者準則
└─ README.md             # 專案說明
```

## 🚀 使用方法

1. 開啟應用
2. 新增一個實例
3. 選擇你的大語言模型 API 提供商
4. 輸入要使用的模型名稱
5. 輸入你的 LLM API Key
6. 輸入你的LLM AppID（可選）
7. 配置一個人格（可選）
8. 開始聊天！

## 🤝 貢獻

我們歡迎各種形式的貢獻！請參閱 [CODE_OF_CONDUCT.md[↗]](CODE_OF_CONDUCT.md) 了解貢獻者行為準則。 

歡迎提交：
- 🐛 Issue - 報告錯誤或提出新功能建議  
- 🔧 Pull Request - 直接貢獻程式碼改進

## 💬 群組
歡迎加入OpenDroidChat Bug回饋/交流群，你可以在這裡交流和回饋問題。  
你可以透過搜尋群號（887447099）的方式或者點選[這裡[↗]](https://qun.qq.com/universal-share/share?ac=1&authKey=DMse5JOJpU4DQbDSXq81177Otr2MtUkJYowAZqhb1AZXkk%2BqV5ZxK7iWaYHbzk3k&busi_data=eyJncm91cENvZGUiOiI4ODc0NDcwOTkiLCJ0b2tlbiI6IjRORVV5VnZpeXRzYjlPOHBBYWViZzJFSkRQeXdLakJFcG9CVU9zbnBYOXJMbGk5RDNHZ3JQbE9iZWU5V1l4MjkiLCJ1aW4iOiI4NTc3MTMxNTAifQ%3D%3D&data=DppwomuajQ62pIIxMwmGWzYlXV0Evn50pE4LwSxUviqpF8mva5LDqOnX3b2WY3Z0cklTpnahpMvCok-VcL6dfA&svctype=4&tempid=h5_group_info)加入群聊。  

如有任何疑問，請發送郵件到 hoe_software_team@outlook.com

## 🔧 開發人員內容
1. 如要建構 Release APK，請參閱 [Release 版本建構指南 (簡體中文)](https://github.com/HOE-Team/OpenDroidChat/blob/main/docs/PERFORM_A_RELEASE_BUILD.md)，如果已經熟悉了 Release 建構流程，可忽略此條提示。

2. OpenDroidChat 使用的 JKS 金鑰檔案指紋：  
請透過以下SHA-256指紋驗證應用的真實性（您可透過keytool -list -v -keystore或APK分析工具比對）：
    - **舊憑證指紋（已停用）**：
        06:F6:A5:21:94:DE:BD:85:71:9E:4F:AD:FB:0F:62:44:BA:07:D7:B3:B3:F0:76:16:E3:EA:AC:A4:1F:9F:10:2D
    - **新憑證指紋（目前生效）**：
        4B:F3:B1:FF:B1:6B:1C:AB:CA:6C:C4:5B:DA:B0:84:4E:48:A9:EA:BC:0A:07:BC:36:CD:71:D0:8E:5A:E7:93:FB

## 📄 許可證

本專案基於 [MIT License[↗]](LICENSE) 開源。

> [!NOTE]
> 這份許可證意味著：
>
> 1. **你可以隨意使用這個專案程式碼**，無論是在個人專案還是商業專案中。
> 2. **你可以修改並重新發布**這個程式碼。
> 3. **你甚至可以用它來開發商業軟體並銷售**，只要你在你的產品中包含原始的 MIT 許可證文字和版權聲明。
> 4. **作者不提供任何保證**，如果使用該軟體導致任何問題，你需要自己承擔風險。

版權所有 © 2025-2026 HOE Team，保留所有權利。  

> [!WARNING]
> ### 著作權聲明
> OpenDroidChat Geometry 系列徽標為 HOE Team 所有，受法律保護。未經明確書面授權，不得用於商業用途或進行修改後使用。
> 
> 舊式應用圖示（OpenDroidChat Bugdroid-Say）由[ChatGPT[↗]](https://chatgpt.com)生成，使用CC BY-NC-SA協議開放

> [!NOTE]
> 由於HOE Team開發人員錯誤的Git操作，導致了安全問題的發生，現已重新建立倉庫以消除安全問題，對各貢獻者及開發者帶來的不便還請諒解，HOE Team將在今後的開發中避免此類安全問題的再次發生。  
> 因為倉庫的重新建立，先前的Releases、Star、Commit和Issue記錄都已不存在，對各位使用者和支援者帶來的困擾我們深感歉意。  
> 注意：本專案已執行金鑰輪換，您可能需要卸載後重新安裝應用