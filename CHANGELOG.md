# Luciq Android SDK Changelog

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