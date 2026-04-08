# Luciq SDK — Setup & Installation

## Overview

Luciq is the Agentic Observability Platform for Mobile. It provides crash reporting, APM (Application Performance Monitoring), bug reporting, session replay, surveys, feature requests, and network logging for Android applications.

**Group ID:** `ai.luciq.library`
**Min SDK:** 19 (21 for Compose modules)

---

## Installation

Add the desired module(s) to your app's dependencies:

**Version Catalog (TOML) — `gradle/libs.versions.toml`:**
```toml
[versions]
luciq = "<version>"

[libraries]
luciq = { group = "ai.luciq.library", name = "luciq", version.ref = "luciq" }
```

**Kotlin DSL — `build.gradle.kts`:**
```kotlin
implementation(libs.luciq)
// or without version catalog:
implementation("ai.luciq.library:luciq:<version>")
```

**Groovy DSL — `build.gradle`:**
```groovy
implementation libs.luciq
// or without version catalog:
implementation 'ai.luciq.library:luciq:<version>'
```

### Available Modules

| Module | Use When |
|--------|----------|
| `luciq` | You want all features (recommended default) |
| `luciq-core` | You only need core functionality (minimal footprint) |
| `luciq-crash` | You only need crash reporting |
| `luciq-apm` | You only need APM |
| `luciq-bug` | You only need bug reporting |
| `luciq-survey` | You only need surveys |
| `luciq-apm-okhttp-interceptor` | You want OkHttp network monitoring |
| `luciq-apm-grpc-interceptor` | You want gRPC network monitoring |
| `luciq-compose` | You use Jetpack Compose UI (Min SDK 21) |
| `luciq-compose-apm` | You want Compose screen/navigation monitoring (Min SDK 21) |

> **Important:** All Luciq modules must use the **same version**. Mixing versions (e.g., `luciq-core:19.4.0` with `luciq-apm:19.2.0`) is unsupported and will cause runtime errors. Use a version catalog or a shared variable to enforce this.

---

## Initialization

Initialize the SDK in your `Application.onCreate()`. Call `build()` only once.

```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Luciq.Builder(this, "APP_TOKEN")
            .setInvocationEvents(LuciqInvocationEvent.SHAKE)
            .build()
    }
}
```

### Builder Options

| Method | Description |
|--------|-------------|
| `setInvocationEvents(LuciqInvocationEvent...)` | How users trigger bug reporting (SHAKE, SCREENSHOT, etc.) |
| `setSdkDebugLogsLevel(LogLevel)` | SDK log verbosity (NONE, ERROR, WARNING, INFO, DEBUG, VERBOSE) |

---

## Permissions

The SDK automatically adds these permissions to your manifest:

```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```

To remove an unwanted permission:

```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" tools:node="remove" />
```

---

## ProGuard / R8

The SDK includes consumer ProGuard rules automatically. No manual configuration is needed for release builds.

If you encounter issues in obfuscated builds, add as a fallback:

```proguard
-keep class ai.luciq.** { *; }
-keepattributes SourceFile,LineNumberTable
```
