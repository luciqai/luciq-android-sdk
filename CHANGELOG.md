# Luciq Android SDK Changelog

## 19.2.1 (Jan 25, 2026)

### Bug Fixes

- Fixes an issue where the bug report form could sometimes open twice after a single shake gesture.
- Fixes an issue where surveys and announcements being obscured by the system navigation bar on certain devices.
- Fixes an issue where canceled network requests being incorrectly logged as errors when using the OkHttp interceptor.
- Fixes a crash that could occur when accessing device storage on devices where external storage is temporarily unavailable.
- Fixes NPE Error Log that could occur during URL connection interception for certain URL types (JAR, file protocols).

---

## 19.2.0 (Jan 12, 2026)

### Features

- **WebView Monitoring**: Added comprehensive WebView monitoring to capture performance metrics, user interactions, and network requests from WebView components.
  - New API: `Luciq.setWebViewMonitoringEnabled(boolean)` - Enable/disable master WebView monitoring
  - New API: `Luciq.setWebViewUserInteractionsTrackingEnabled(boolean)` - Enable/disable user interaction tracking in WebViews
  - New API: `Luciq.setWebViewNetworkTrackingEnabled(boolean)` - Enable/disable network logging from WebViews
  - New build configuration: `luciq { webViewsTrackingEnabled = true }` - Enable WebView bytecode transformation
  - WebView interactions and network requests now appear in bug reports with source identification
  - Captured data includes performance traces, user steps, and network logs

- **Notification Icon Color API**: Introduces `Replies.setNotificationIconColor()` to customize the background color of notification icons for Luciq chat notifications.
```kotlin
Replies.setNotificationIconColor(Color.RED)
Replies.setNotificationIconColor(context.resources.getColor(R.color.my_color))
```

### Enhancements

- Adds defensive validation to prevent sending corrupt app version data under certain conditions.

### Bug Fixes

- Fixes a race condition that could cause duplicate chat sync network requests under certain conditions.
- Fixes an issue where proactive bug reporting does not trigger after user termination under certain conditions.
- Fixes a bug causing logs and attachments to go missing with NDK crash reports under certain conditions.
- Fixes a bug causing attachments not to be sent with reports under certain conditions.
- Fixes a bug in Feature Requests where after submitting a new FR, a confirmation dialogue appears behind the success dialogue.
- Fixes the attached Luciq logs while using the `onReportSubmitHandler`.
- Fixes the attached tags while using the `onReportSubmitHandler`.
- Fixes the attached user attributes while using the `onReportSubmitHandler`.
- Fixes ProtectionLayout appearing repeatedly on each interaction with Composables when using `androidx.core:core-ktx` v1.16.0+.
- Fixes button text color in chats to ensure proper visibility.
- Fixes an issue where `setOnInvokeCallback` was not triggered when called before opening the bug reporting dialog.
- Fixes alert dialogs appearing with a white background in dark mode when using a custom theme.

---

## 19.1.0 (Dec 25, 2025)

### Features

- **Session Replay Screenshot Quality Control**: New API to adjust the visual quality of captured screenshots:
```kotlin
// Available quality options:
// - ScreenshotQuality.NORMAL (default) - 25% compression, balanced size/quality
// - ScreenshotQuality.HIGH - 50% compression, better clarity
// - ScreenshotQuality.GREYSCALE - 25% compression, reduced color data
SessionReplay.setScreenshotQuality(ScreenshotQuality.NORMAL)
```

- **Video-like Session Replay** *(Experimental)*: Capture screenshots based on user interactions or at fixed intervals for a video-like playback experience:

>[!WARNING]
> Video-like Session Replay may capture sensitive UI unmasked in some cases. USE AT YOUR OWN DISCRETION TO PREVENT PII LEAKAGE. Opt in ONLY after validating masking/privacy in your app.

```kotlin
@OptIn(ExperimentalVideoLikeReplay::class)
fun configureVideoLikeSessionReplay() {
    // Set capturing mode
    // - CapturingMode.NAVIGATION (default) - Captures on screen changes
    // - CapturingMode.INTERACTIONS - Captures on user interactions
    // - CapturingMode.FREQUENCY - Captures at fixed intervals
    SessionReplay.setCapturingMode(CapturingMode.FREQUENCY)

    // Set screenshot capture interval (only applies to CapturingMode.FREQUENCY)
    // Value in milliseconds. Minimum: 500ms, Default: 1000ms
    SessionReplay.setScreenshotCaptureInterval(500)
}
```
> [!NOTE]
> Screens with `FLAG_SECURE` are automatically excluded. On low-performance devices, only NAVIGATION mode is available.

For more details, see our [Session Replay documentation](https://docs.luciq.ai/docs/android-session-replay).

### Enhancements

- Feature Flags' keys and values limits are now server-side configurable.
- Introduces actionable user consents for Bug Reporting with a new `NO_AUTOMATIC_BUG_GROUPING` action type
```kotlin
BugReporting.addUserConsent(
    "grouping-consent",            // unique key
    "Allow grouping similar bugs", // description (max 200 chars)
    false,                         // isMandatory
    true,                          // isChecked (default state)
    ActionType.NO_AUTOMATIC_BUG_GROUPING
)
```
Available action types: `DROP_AUTO_CAPTURED_MEDIA`, `DROP_LOGS`, `NO_CHAT`, `NO_AUTOMATIC_BUG_GROUPING`

### Bug Fixes

- Fixes a bug where keyboard open/close detection caused cyclic `onGlobalLayout` callbacks when adjusting the bottom sheet margin in Bug Reporting.

## 19.0.1 (Dec 21, 2025)

> [!WARNING]
> Includes a critical fix for Compose v1.9.0+ users.

### Bug Fixes

- Fixes a critical incompatibility with Jetpack Compose v1.9.0+ that could prevent exceptions and crashes from being propagated.

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