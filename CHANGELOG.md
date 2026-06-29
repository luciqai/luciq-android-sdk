# Luciq Android SDK Changelog

## 19.9.1 (June 29, 2026)

### Enhancements

- [Internal] Enhances SDK internal analytics

## 19.9.0 (June 28, 2026)

### Enhancements

- Adds partial-success flagging for GraphQL responses in Bug Reports and Session Replay.
- Improves SDK network efficiency.

## 19.8.0 (June 9, 2026)

### Features

- Added the ability to start a new session on logout so each user identity gets an isolated session, ensuring activity is attributed to the correct user. This is disabled by default — reach out to customer support team to enable it.

### Bug Fixes

- Fixes a rare ANR that could occur at crash time due to a lock-ordering deadlock while a session was being ended.
- Fixes SDK diagnostic events — including network and Session Replay logs — being silently dropped on obfuscated (release) builds.
- Fixes an empty (0-byte) `luciq_config.json` bundled in the SDK that could break post-build security / app-shielding tools.

## 19.7.1 (June 3, 2026)

### Features

- Adds the `setCapturingContainerEnabled` API to control whether container screens (Activities, Fragments, and DialogFragments) generate Repro Steps screenshots.

### Bug Fixes

- Fixes a rare crash during screenshot capture when an Activity is being destroyed.

## 19.7.0 (May 13, 2026)

### Features

- Adds the `setViewsContentCaptureEnabled` API to redact User Steps and Repro Steps content.
- Adds support for params in user events.
- Adds a Gradle configuration to automatically capture Jetpack Compose screens as APM screens.
```kotlin
luciq {
  showAutoComposableAsScreen = true
}
```

### Enhancements

- Adds support for per-variant Luciq app tokens in the luciq-crash Gradle plugin.
- Optimizes console log lock contention that could block the main thread and cause ANRs.

### Bug Fixes

- Fixes an ANR caused by thread-safety race in OkHttp network logging under concurrent traffic.
- Fixes data capture in the inapp_webview plugin by instrumenting WebView subclasses.
- Fixes OOM crashes in long background sessions by applying backoff on sync failure.

## 19.6.0

### New Features

- Track user actions in React native webviews 
- Support Screen Loading For React Native

### Enhancements

- Improve Accessibility in Bug Reporting screen

### Bug Fixes
- Fix host app crashes caused by RxJava UndeliverableException 
- Fix deadlock between the APM ServiceLocator.class monitor and Kotlin by lazy internal locks which causes ANR in Flutter apps. 
- Fix memory leak in screen rendering caused by an unbounded per-instance map and prevent
  Window/Activity retention

## 19.5.1 (Apr 19, 2026)

### Bug Fixes

- Fixes an ANR that could occur when non-HTTP URL connections (such as classpath resource loading via Kotlin reflection) were being intercepted by the network monitor.
- Fixes an ANR that could occur when using the OkHttp network interceptor due to concurrent access from multiple threads during a network call.

---

## 19.5.0 (Apr 15, 2026)

### New Features

- Adds support for branching in Custom Surveys.
- Adds NDK symbol upload Gradle task for automated symbol file (.so) upload during the build process. Check out the [docs](https://docs.luciq.ai/android/set-up-luciq-for-android/set-up-crash-reporting/deobfuscation-for-android#deobfuscating-ndk-c-crashes) for more details.
```kotlin
luciq {
    crashReporting {
        appToken = "APP_TOKEN"
        ndkSymbol {
            autoUploadEnabled = true
            apiKey = "API_KEY"
        }
    }
}
```

### Enhancements

- Adds support for excluding custom URL patterns from OkHttp response body capture.
```kotlin
luciq {
    networkInterception {
        enabled = true
        excludeUrlPatterns = listOf(".*chat/stream.*", ".*/events.*", ".*/notifications/live.*")
        okHttp {
            it.enabled = true
        }
    }
}
```

### Bug Fixes

- Fixes OkHttp interceptor breaking streaming/chunked HTTP responses (SSE/event-stream) by skipping response body buffering for streaming content types.

---

## 19.4.0 (Apr 1, 2026)

### Features & Enhancements

- Adds satellite network type detection for Session Profiler and APM network logs, providing better visibility into network connectivity.
- Adds Multi-window support in Surveys and Announcements.
- Adds support for Compose BOM version 2026.03.00 (Compose 1.10.5).
- Improved internal SDK logging and error handling to reduce noise.

---

## 19.3.0 (Mar 4, 2026)

### Features

- **Custom Spans in APM**: Adds support for Custom Spans to track custom operations with explicit start and end times.
```kotlin
// Start custom span
val span: CustomSpan? = APM.startCustomSpan(name: string)
// ... do work ...
span?.end()  //end custom span

// Save completed custom span with known start and end date
APM.addCompletedCustomSpan(name: String, startDate: Date, endDate: Date)
```

### Enhancements

- Adds default masking for WebView content to enhance privacy protection.
- Reduces unnecessary error log output when reporting non-fatal exceptions.
- Changes the ADB debug property key from `debug.instabug.apm.app` to `debug.luciq.app`. The old property is still supported as a fallback.
- Changes error messaging when adding Feature Requests while offline to display a toast with clearer guidance.

### Bug Fixes

- Fixes an ANR caused by synchronous SharedPreferences access in the APM ContentProvider during app startup.
- Fixes an ANR caused by Firebase initialization deadlock when Firebase Performance SDK is enabled by excluding Firebase network requests from APM interception.
- Fixes a crash that occurs under certain conditions when invoking the SDK via the Floating Action Button.
- Fixes malformed JSON in crash reports when message content exceeds size limits.
- Fixes an issue where ScreenRendering traces were incorrectly attributed in specific scenarios.
- Fixes a crash in Replies UI that occurs under certain conditions when the screen is closed unexpectedly.

---

## 19.2.2 (Feb 18, 2026)

### Enhancements

- Adds `LogLevel.WARNING` log level for filtering SDK warning logs. This is now the default log level.

> [!WARNING]
> The numeric values of `LogLevel.DEBUG` and `LogLevel.VERBOSE` have changed (`DEBUG`: 2 → 3, `VERBOSE`: 3 → 4). If you are using hardcoded integers instead of `LogLevel` constants, update your code to use the constants directly (e.g., `LogLevel.DEBUG` instead of `2`).

- Resolves a security warning by replacing raw SQL construction with parameterized queries in internal diagnostics storage.

### Bug Fixes

- Fixes a build-time crash in the Gradle plugin when logging ASM bytecode transformations for certain ClassVisitor types.
- Fixes an issue where user steps data scraping captured incorrect view IDs for legacy views inside private scope containers.
- Fixes a crash that could occur when displaying chat messages with a null attachment type.
- Fixes potential null pointer exceptions in bug reporting UI when fragment views are accessed after destruction.

---

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
