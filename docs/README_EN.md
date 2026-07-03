<h1 align="center">OpenDroidChat</h1>

<div align="center">
    <img width="150" src="../images/odc-logo.png" alt="Project Logo">
</div>

<h3 align="center">An open-source, lightweight LLM API chat client for Android 6+</h3>

<p align="center"><s>Wow, even an API quota waster app</s></p>

<div align="center">
  
[![Stars](https://img.shields.io/github/stars/HOE-Team/OpenDroidChat?style=for-the-badge&logo=data:image/svg%2bxml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEiIHdpZHRoPSIxNiIgaGVpZ2h0PSIxNiI+PHBhdGggZD0iTTggLjI1YS43NS43NSAwIDAgMSAuNjczLjQxOGwxLjg4MiAzLjgxNSA0LjIxLjYxMmEuNzUuNzUgMCAwIDEgLjQxNiAxLjI3OWwtMy4wNDYgMi45Ny43MTkgNC4xOTJhLjc1MS43NTEgMCAwIDEtMS4wODguNzkxTDggMTIuMzQ3bC0zLjc2NiAxLjk4YS43NS43NSAwIDAgMS0xLjA4OC0uNzlsLjcyLTQuMTk0TC44MTggNi4zNzRhLjc1Ljc1IDAgMCAxIC40MTYtMS4yOGw0LjIxLS42MTFMNy4zMjcuNjY4QS43NS43NSAwIDAgMSA4IC4yNVoiIGZpbGw9IiNlYWM1NGYiLz48L3N2Zz4=&logoSize=auto&label=Stars&labelColor=444444&color=eac54f)](https://github.com/HOE-Team/OpenDroidChat)
[![LICENSE](https://img.shields.io/github/license/HOE-Team/OpenDroidChat?style=for-the-badge)](https://github.com/HOE-Team/OpenDroidChat/blob/main/LICENSE)
![GitHub Release](https://img.shields.io/github/v/release/HOE-Team/OpenDroidChat?label=Release&logo=github&style=for-the-badge)

**[简体中文](../README.md) | English | [繁體中文](README_ZHTW.md)**
</div>

> [!IMPORTANT]
> OpenDroidChat is in crisis! Visit  and learn about [KeepAndroidOpen[↗]](https://keepandroidopen.org) — oppose Google’s policy of forcing mandatory verification for all Android developers, which undermines the open foundation of Android.

> [!NOTE]
> The program translation is not yet complete. If you are able to help localize the OpenDroidChat project, feel free to send us a [Pull Request[↗]](https://github.com/HOE-Team/OpenDroidChat/pulls).

## 🔨 Tech Stack

* [**Kotlin**[↗]](https://kotlinlang.org/) — Primary programming language
* [**Jetpack Compose**[↗]](https://developer.android.com/jetpack/compose) — Declarative UI framework
* [**Material Design 3 (M3)**[↗]](https://m3.material.io/) — Native GUI design implementation
* [**Gradle**[↗]](https://gradle.org/) — Build tool
* [**Markwon**[↗]](https://github.com/noties/Markwon) — For rendering Markdown in LLM messages
* [**OkHttp**[↗]](https://square.ac.cn/okhttp/) — Efficient, powerful Java/Android HTTP client.
* [**Ktor**[↗]](https://ktor.io/) — Kotlin coroutine-based asynchronous backend framework, supporting multi-platform.

## ✨ Features

* Supports Android 6.0 (API level 23) and above
* Native, beautiful Material 3 Expressive interface
* **Markdown message rendering** (code blocks, headings, links, etc.)
* Lightweight, suitable for devices with limited space
* Compatible with various LLM APIs
* Uses encryption to protect user LLM API Keys
* Enables model thinking to improve AI output accuracy

> [!NOTE]
>
> ### Pre-configured LLM Providers
>
> * [x] OpenAI / Azure OpenAI
> * [x] Google Gemini
> * [x] DeepSeek
> * [x] Dashscope (Aliyun Bailian)
> * [x] Claude 

## 📥 Installation

1. Download the APK file from [Releases[↗]](https://github.com/HOE-Team/OpenDroidChat/releases)
2. (Recommended) Verify the integrity of the installation package
3. Install it using any package installer

## 🛠️ Building from Source

Ensure you have **Android Studio** or **Gradle** installed.

> [!IMPORTANT]
>
> * You may need a VPN or proxy to download external resources required by Gradle.
> * You may need a computer with sufficient performance to complete the build.

### 🔧 Basic Build (Debug Version)

```bash
# Clone the repository
git clone https://github.com/HOE-Team/OpenDroidChat.git
cd OpenDroidChat

# Build the Debug version using Gradle Wrapper
./gradlew assembleDebug    # Linux / macOS
gradlew.bat assembleDebug   # Windows
```

After a successful build, the APK will be located in `app/build/outputs/apk/`.

**For Release builds, please refer to the [Release Build Guide[↗]](PERFORM_A_RELEASE_BUILD_EN.md)**


## 📁 Project Structure

```
project/
├─ app/                  # Source code
├─ docs/                 # Documentation
├─ gradle/               # Gradle configuration
├─ build.gradle.kts      # Project build configuration
├─ settings.gradle.kts   # Gradle settings
├─ gradlew               # Gradle Wrapper (Linux / macOS)
├─ gradlew.bat           # Gradle Wrapper (Windows)
├─ LICENSE               # License
├─ CODE_OF_CONDUCT.md    # Contributor Covenant
└─ README.md             # Project README
```

## 🚀 Usage

1. Open the app
2. Add an instance
3. Select your LLM API provider
4. Enter the model name you intend to use
5. Enter your LLM API Key
6. Enter your LLM AppID (optional)
7. Configure a persona (optional)
8. Start chatting!

## 🤝 Contributing

We welcome contributions of all forms! Please see [CODE_OF_CONDUCT.md[↗]](CODE_OF_CONDUCT.md) for our contributor code of conduct.

Feel free to submit:
- 🐛 Issues - to report bugs or suggest new features
- 🔧 Pull Requests - to directly contribute code improvements

## 💬 Community Group
Welcome to join the OpenDroidChat Bug Feedback/Discussion group, where you can communicate and report issues.  
You can join by searching for the group number (`887447099`) on QQ or by clicking [here[↗]](https://qun.qq.com/universal-share/share?ac=1&authKey=DMse5JOJpU4DQbDSXq81177Otr2MtUkJYowAZqhb1AZXkk%2BqV5ZxK7iWaYHbzk3k&busi_data=eyJncm91cENvZGUiOiI4ODc0NDcwOTkiLCJ0b2tlbiI6IjRORVV5VnZpeXRzYjlPOHBBYWViZzJFSkRQeXdLakJFcG9CVU9zbnBYOXJMbGk5RDNHZ3JQbE9iZWU5V1l4MjkiLCJ1aW4iOiI4NTc3MTMxNTAifQ%3D%3D&data=DppwomuajQ62pIIxMwmGWzYlXV0Evn50pE4LwSxUviqpF8mva5LDqOnX3b2WY3Z0cklTpnahpMvCok-VcL6dfA&svctype=4&tempid=h5_group_info) to join the group.  

For any inquiries, please email hoe_software_team@outlook.com

## 🔧 Developer Content
1. To build a Release APK, please refer to the [Release Build Guide (Simplified Chinese)](https://github.com/HOE-Team/OpenDroidChat/blob/main/docs/PERFORM_A_RELEASE_BUILD.md). If you are already familiar with the Release build process, you can ignore this tip.

2. JKS keystore fingerprint used by OpenDroidChat:  
Please verify the authenticity of the application using the following SHA-256 fingerprints (you can compare using `keytool -list -v -keystore` or APK analysis tools):
    - **Old certificate fingerprint (decommissioned)**:
        06:F6:A5:21:94:DE:BD:85:71:9E:4F:AD:FB:0F:62:44:BA:07:D7:B3:B3:F0:76:16:E3:EA:AC:A4:1F:9F:10:2D
    - **New certificate fingerprint (currently active)**:
        4B:F3:B1:FF:B1:6B:1C:AB:CA:6C:C4:5B:DA:B0:84:4E:48:A9:EA:BC:0A:07:BC:36:CD:71:D0:8E:5A:E7:93:FB

## 📄 License

This project is open-sourced under the [MIT License[↗]](LICENSE).

> [!NOTE]
> This license means:
>
> 1. **You are free to use this project's code**, whether in personal or commercial projects.
> 2. **You can modify and redistribute** this code.
> 3. **You can even use it to develop and sell commercial software**, as long as you include the original MIT license text and copyright notice in your product.
> 4. **The authors provide no warranty**. You assume all risks associated with using this software.

Copyright © 2025-2026 HOE Team. All rights reserved.  

> [!WARNING]
> ### Copyright Notice
> The OpenDroidChat Geometry series logos are owned by HOE Team and protected by law. They may not be used for commercial purposes or modified without explicit written authorization.
> 
> The old app icon (OpenDroidChat Bugdroid-Say) was generated by [ChatGPT[↗]](https://chatgpt.com) and is open under the CC BY-NC-SA license.

> [!NOTE]
> Due to an erroneous Git operation by the HOE Team developers, a security issue occurred. The repository has been recreated to eliminate this security concern. We apologize for any inconvenience caused to contributors and developers. The HOE Team will take measures to prevent such security issues in future development.  
> Because the repository was recreated, all previous Releases, Stars, Commits, and Issues records no longer exist. We deeply apologize for the trouble caused to our users and supporters.  
> Note: This project has undergone key rotation. You may need to uninstall and reinstall the application.
