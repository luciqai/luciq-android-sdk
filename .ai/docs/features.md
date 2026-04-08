# Luciq SDK — Feature APIs

Most Luciq public APIs are **non-blocking** — they queue work on a background executor and return immediately. Some getter/query APIs (e.g., `Luciq.getUserData()`, `Surveys.getAvailableSurveys()`) execute **synchronously** and return values directly via `APIChecker.checkAndGet()`. Feature availability may depend on your plan — check the Luciq dashboard or contact support if a feature isn't working.

---

## Crash Reporting

```kotlin
// Enable crash reporting
CrashReporting.setState(Feature.State.ENABLED)

// Enable ANR (Application Not Responding) detection
CrashReporting.setAnrState(Feature.State.ENABLED)

// Report a non-fatal exception
try {
    riskyOperation()
} catch (e: Exception) {
    CrashReporting.report(e)
}

// Report with custom grouping fingerprint
CrashReporting.report(IllegalStateException("Payment failed"), "payment-flow")
```

---

## APM (Application Performance Monitoring)

```kotlin
APM.setEnabled(true)

// UI Traces — track screen rendering performance
APM.startUITrace("screen_name")
// ... user interacts with the screen ...
APM.endUITrace()
```

---

## Bug Reporting

```kotlin
// Set invocation events
BugReporting.setInvocationEvents(LuciqInvocationEvent.SHAKE, LuciqInvocationEvent.SCREENSHOT)

// Enable/disable
BugReporting.setState(Feature.State.ENABLED)

// Programmatically show bug reporter
BugReporting.show(BugReporting.ReportType.BUG)
```

---

## Surveys

```kotlin
Surveys.setState(Feature.State.ENABLED)
```

---

## Feature Requests

```kotlin
FeatureRequests.show()
FeatureRequests.setState(Feature.State.ENABLED)
```

---

## User Identification

```kotlin
// Identify the current user
Luciq.identifyUser("username", "user@example.com", "user-id-123")

// Set custom user attributes
Luciq.setUserAttribute("subscription", "premium")

// Log a user event
Luciq.logUserEvent("completed_onboarding")
```

---

## Replies (In-App Chat)

```kotlin
Replies.show()
Replies.setState(Feature.State.ENABLED)
```
