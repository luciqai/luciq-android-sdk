# Luciq SDK — Integration Guide

## OkHttp Network Monitoring

### Option A: Gradle Plugin (Recommended)

The Luciq Gradle plugin automatically instruments OkHttp for network logging — no code changes needed.

**Kotlin DSL — `build.gradle.kts`:**
```kotlin
plugins {
    id("luciq")
}
```

**Groovy DSL — `build.gradle`:**
```groovy
plugins {
    id 'luciq'
}
```

### Option B: Manual Interceptor

If you prefer manual setup, add the interceptor to your OkHttp client:

**Version Catalog (TOML) — `gradle/libs.versions.toml`:**
```toml
[versions]
luciq = "<version>"

[libraries]
luciq-apm-okhttp = { group = "ai.luciq.library", name = "luciq-apm-okhttp-interceptor", version.ref = "luciq" }
```

**Kotlin DSL — `build.gradle.kts`:**
```kotlin
implementation(libs.luciq.apm.okhttp)
// or without version catalog:
implementation("ai.luciq.library:luciq-apm-okhttp-interceptor:<version>")
```

**Groovy DSL — `build.gradle`:**
```groovy
implementation libs.luciq.apm.okhttp
// or without version catalog:
implementation 'ai.luciq.library:luciq-apm-okhttp-interceptor:<version>'
```

```kotlin
val client = OkHttpClient.Builder()
    .addInterceptor(LuciqAPMOkhttpInterceptor())
    .build()
```

GraphQL request names are automatically captured when using the OkHttp interceptor.

> Make sure `APM.setEnabled(true)` is called for network monitoring to work.

---

## gRPC Network Monitoring

**Version Catalog (TOML) — `gradle/libs.versions.toml`:**
```toml
[libraries]
luciq-apm-grpc = { group = "ai.luciq.library", name = "luciq-apm-grpc-interceptor", version.ref = "luciq" }
```

**Kotlin DSL — `build.gradle.kts`:**
```kotlin
implementation(libs.luciq.apm.grpc)
// or without version catalog:
implementation("ai.luciq.library:luciq-apm-grpc-interceptor:<version>")
```

**Groovy DSL — `build.gradle`:**
```groovy
implementation libs.luciq.apm.grpc
// or without version catalog:
implementation 'ai.luciq.library:luciq-apm-grpc-interceptor:<version>'
```

Add the Luciq gRPC interceptor to your channel builder:

```kotlin
val channel = ManagedChannelBuilder.forAddress("host", port)
    .intercept(LuciqAPMGrpcInterceptor())
    .build()
```

---

## Jetpack Compose

Compose support requires **Min SDK 21**.

**Kotlin DSL — `build.gradle.kts`:**
```kotlin
implementation("ai.luciq.library:luciq-compose:<version>")       // Compose UI components
implementation("ai.luciq.library:luciq-compose-apm:<version>")   // Compose screen/navigation monitoring
```

**Groovy DSL — `build.gradle`:**
```groovy
implementation 'ai.luciq.library:luciq-compose:<version>'       // Compose UI components
implementation 'ai.luciq.library:luciq-compose-apm:<version>'   // Compose screen/navigation monitoring
```

`luciq-compose-apm` automatically tracks Compose screen transitions and navigation events for APM.

---

## Upload Mapping Files (ProGuard/R8)

For symbolicated crash reports in release builds, upload your mapping files.

### Via Gradle Plugin (Recommended)

The Luciq Gradle plugin automatically uploads mapping files after each release build:

**Kotlin DSL — `build.gradle.kts`:**
```kotlin
plugins {
    id("luciq")
}

luciq {
    uploadMappingFiles = true
}
```

**Groovy DSL — `build.gradle`:**
```groovy
plugins {
    id 'luciq'
}

luciq {
    uploadMappingFiles = true
}
```

### Via Manual Upload

Upload mapping files from `app/build/outputs/mapping/<variant>/mapping.txt` through the Luciq dashboard or API.

---

## Gradle Plugin

The Luciq Gradle plugin provides:
- **Automatic network interception** — instruments OkHttp without manual interceptor setup
- **Bytecode manipulation** — injects SDK hooks at build time
- **Mapping file upload** — uploads ProGuard/R8 mapping files for symbolicated crash reports

**Kotlin DSL — `build.gradle.kts`:**
```kotlin
plugins {
    id("luciq")
}
```

**Groovy DSL — `build.gradle`:**
```groovy
plugins {
    id 'luciq'
}
```

This is the recommended approach for most apps. It reduces boilerplate and ensures all network calls are captured.

---

## Multi-Module Apps

For multi-module Android projects:
- Add the `luciq` (or specific feature module) dependency to the **app module** where you call `Luciq.Builder(...).build()`
- Add `luciq-apm-okhttp-interceptor` to any module that creates OkHttp clients (if using manual interceptor setup)
- The Gradle plugin should be applied to the **app module**

> **Important:** All Luciq modules must use the **same version** across all modules. Use a version catalog (`libs.versions.toml`) to enforce this.
