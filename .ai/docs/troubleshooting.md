# Luciq SDK — Troubleshooting

## Enable Debug Logging

First step for any issue — enable verbose SDK logging:

```kotlin
Luciq.Builder(this, "APP_TOKEN")
    .setSdkDebugLogsLevel(LogLevel.VERBOSE)
    .build()
```

Filter logs in logcat:
```bash
adb logcat | grep "LUCIQ-"
```

**Log Levels:** NONE, ERROR (default), WARNING, INFO, DEBUG, VERBOSE

---

## Common Issues

### SDK Not Initializing

| Symptom | Cause | Fix |
|---------|-------|-----|
| "Invalid SDK token" | Wrong or missing token | Verify token in Luciq dashboard |
| "Application context is null" | Not passing Application | Pass `this` from `Application.onCreate()` |
| "Luciq already built" | `build()` called more than once | Call `build()` only once in `Application.onCreate()` |

### Feature Not Working

Features are controlled by your plan. Enable debug logging and check for messages like:
- `"APM not available for your plan"`
- `"Crash reporting not available"`

Contact Luciq support if a feature should be available but isn't.

### Crashes Not Being Reported

1. Verify crash reporting is enabled: `CrashReporting.setState(Feature.State.ENABLED)`
2. Check network connectivity — reports are uploaded over the network
3. Test with a manual crash: `throw RuntimeException("Test crash")`
4. Check logs for `LUCIQ-CR` tag: `adb logcat | grep "LUCIQ-CR"`

### ANRs Not Being Detected

1. Verify ANR detection is enabled: `CrashReporting.setAnrState(Feature.State.ENABLED)`
2. ANR detection may be suppressed when a debugger is attached
3. Test by blocking the main thread for 10+ seconds

### Network Requests Not Logged

1. Verify `APM.setEnabled(true)` is called
2. **If using Gradle plugin:** Ensure `plugins { id("luciq") }` is in your app module
3. **If using manual interceptor:** Ensure `LuciqAPMOkhttpInterceptor` is added to your OkHttp client
4. Check logs: `adb logcat | grep "LUCIQ-APM"`

### Release Build Issues (ProGuard/R8)

SDK consumer ProGuard rules are included automatically. If SDK features break in release builds:

```proguard
// Add to your proguard-rules.pro as a fallback
-keep class ai.luciq.** { *; }
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
```

### Performance Concerns

For specific performance benchmarks (SDK launch time, CPU/memory overhead), consult the official documentation at [docs.luciq.ai](https://docs.luciq.ai) for up-to-date specifications.

General guidance:
- SDK initialization is lightweight — most work is deferred
- Most public APIs are non-blocking (queued on background threads)
- Do not call SDK APIs in a tight loop

### Storage Growing

The SDK uploads data automatically and purges it after successful upload. If storage is growing:
- Check network connectivity
- Check for rate limiting: `adb logcat | grep "RateLimitedException"`
- Data should be uploaded in batches when connectivity is available

---

## Log Patterns

**Normal operation:**
```
Luciq: SDK initialized successfully
APM: APM enabled
CrashReporting: Crash reporting enabled
```

**Warning signs:**
```
ERROR: Crash reporting not available for your plan
ERROR: Failed to store crash report: [error]
WARNING: Upload failed: [error]
ERROR: RateLimitedException: Too many requests
```

---

## Getting Help

When contacting Luciq support, provide:
1. SDK version (from your `build.gradle`)
2. Full logs: `adb logcat -v time > logs.txt` (5-10 minutes covering the issue)
3. Android version and device model
4. Steps to reproduce
