# Luciq Android SDK Changelog

## 19.0.0 (Dec 11, 2025)

### Breaking Changes
- Introducing a new, single network interceptor with automatic instrumentation for OkHttp and native UrlConnection. To enable network interception, gradle plugin should be configured:
```kotlin
// Project-level build.grade
plugins {
    id("ai.luciq.library") version "$luciqVersion" apply false 
}
```
```kotlin

// app-level build.gradle
plugins {
    id("luciq")
}
luciq {
    networkInterception {
        enabled = true // enables both of OKHttp and UrlConnection integration, defaults to false
    }
}
dependencies {
    // For OkHttp Support
    implementation("ai.luciq.library:luciq-with-okhttp-interceptor:19.x.x")
    implementation("ai.luciq.library:luciq-apm-okhttp-interceptor:19.x.x")
}
```
A detailed migration guide can be found [here](https://docs.luciq.ai/docs/android-unified-network-interception)

>[!WARNING]
> Deprecated legacy interception is still available but switched off by default in favor of the new unified interception. To keep using the legacy interception please contact support to turn it on for your apps, but it’s planned to be removed in the future, so it’s not recommended.

- Revamped APM's UI Hangs with slow and frozen frames metric. If UI Hangs' APIs are in use, they should be replaced with Screen Rendering APIs as follows:
```kotlin
APM.setUIHangEnabled() // Deprecated and should be replaced with 
APM.setScreenRenderingEnabled()
```
More details about screen rendering can be found in our [docs](https://docs.luciq.ai/docs/android-apm-screen-rendering)

>[!WARNING]
> `APM.setUIHangEnabled()` is deprecated and capturing hangs is completely shutdown in favor of the new metric.

### Bug Fixes
- Fixed a race condition causing `android.database.sqlite.SQLiteConstraintException` error to be logged under certain conditions.

## 18.2.1 (Nov 26, 2025)

### Enhancements
- Cleaned up and refined logging levels for non-critical events.

### Bug Fixes
- Fixed incorrect UI translation when the device locale is set to Portuguese (Brazil) pt_BR.
    English String: Press record when ready
    Incorrect Translation: Quando estiver pronto, prima Gravar
    Correct Translation: Quando estiver pronto, comece a gravar.

- Fixed Out of Memory (OOM) issues during chat messages synchronization by optimizing memory allocation and resource management in chats SynchronizationManager.


## 18.2.0 (Nov 13, 2025)

### Enhancements
- Added support for app variant targeting in Surveys and Announcements to enable Cross-platform & Native App customers to target users by both app version and app variant
```
// Set app variant during SDK initialization
    new Instabug.Builder(this, "APP_TOKEN")
                .setAppVariant("App1")  // Set your app variant
                .build();
```
- Increased character limit for disclaimer and consent fields in bug reporting to 200 chars

### Bug Fixes
- Fixed an issue that caused missing screenshots in Repro Steps for Flutter apps.
- Fixed an issue where, under certain conditions, the first UI trace wasn’t inserted into the database, leading to SqliteConstraintException logs.
- Fixed an issue where a white box covered the bug description field on small-screen devices.
- Fixed a Disk Read StrictMode violation that occurred when attaching images from the gallery in Bug Reporting.
- Fixed an issue where BugReporting.setDisclaimerText() did not apply if called immediately after SDK initialization.


## 18.1.0 (Oct 30, 2025)

### Enhancements
- Adds test tags support for Compose v1.7.+
- Adds support for Apollo v4

### Bug Fixes
- Fixes missing request and response headers in `NetworkLogSnapshot` when using `APM.registerNetworkLogsListener(listener)`.
- Fixes header changes done using `APM.registerNetworkLogsListener(listener)` not being applied to the captured network data.
- Fixes attributes added using `OnNetworkTraceListener.addAttributesOnFinish()` are not added in some cases.

## 18.0.1 (Oct 10, 2025)

### Enhancements
- Optimizes SDK's `UncaughtExceptionHandler` operations to prevent ANRs from occurring while the application is exiting (crashing).
- Mutes a few diagnostics logs to reduce noise in the logcat.

### Bug Fixes
- Fixes an issue where features cache was being stored before features states were properly consumed, which could lead to inconsistent SDK behavior under special conditions.
- Fixes an issue with email format validation causing malformed emails to be sent with crash reports.
- Fixes a crash, `ActivityNotFoundException`, encountered while attaching extra images to bug reports via Android's [new photo picker](https://developer.android.com/training/data-storage/shared/photo-picker) on some devices by falling back to the legacy photo picker instead.
- Fixes a race condition that causes chats synchronization request to get fired while chats being disabled with `Replies` APIs.
- Fixes an issue that causes interactions tracking for Jetpack Compose screens to yield inaccurate results when a overlay Composable is present.

## 18.0.0 (Aug 24, 2025)

### Breaking Changes
- SDK rebranded from _Instabug_ to _Luciq_. To migrate, visit https://docs.luciq.ai/docs/android-luciq-migration for guidance.