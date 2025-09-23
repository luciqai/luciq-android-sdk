# Luciq Android SDK

[![Maven Central](https://img.shields.io/maven-central/v/ai.luciq.library/luciq.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22ai.luciq.library%22%20AND%20a:%22luciq%22)

Luciq is the Agentic Observability Platform for Mobile.

Go beyond traditional bug and crash reporting. Our intelligent AI agents help you capture rich, contextual data for every issue; including full session replays, console logs, and detailed network requests, to proactively detect, prioritize, and resolve problems automatically. From a simple shake for user feedback to reliable, full-stack crash reporting, Luciq provides the complete picture.

This empowers your team to ship faster, deliver frustration-free user sessions, and focus on building what matters.

For more info, visit Luciq.ai.

## Installation

### Gradle

Add this line to your build.gradle file.

```groovy
implementation 'ai.luciq.library:luciq:<latest-version>'
```

## Usage

In your `Application` class add this line to your `onCreate` method.

**Java**
```java
new Luciq.Builder(this, "APP_TOKEN").build();
```
**Kotlin**
```kotlin
Luciq.Builder(this, "APP_TOKEN").build()
```

## Notes

Some permissions are automatically added to your AndroidManifest.xml file. Some of them are required to be able to fetch some information like the network and wifi connection. Others are used to allow the user to attach images, videos, and audio recordings.

Generally, the permission request doesn't appear unless the user attempts to use any of the features requiring the permission. The only exception, if you set the invocation event to be Screenshot. Then, the storage permission will be requested when the application launches.

This behavior is happening with the screenshot invocation because there isn't any native event that tells the SDK that a screenshot has been captured. The only way to do it is to monitor the screenshots directory. The SDK is invoked once a screenshot is added to the directory while the application is active.

```xml
<uses-permission android:name=“android.permission.ACCESS_NETWORK_STATE” />
<uses-permission android:name=“android.permission.WRITE_EXTERNAL_STORAGE” />
<uses-permission android:name=“android.permission.READ_EXTERNAL_STORAGE” />
<uses-permission android:name=“android.permission.ACCESS_WIFI_STATE” />
```

You can remove any of the permissions if you are not willing to use the feature associated with it as in the following example.

```xml
<uses-permission android:name=“android.permission.WRITE_EXTERNAL_STORAGE” tools:node=“remove”/>
```

## More

You can also check out our [API Reference](https://docs.luciq.ai/reference) for more detailed information about our SDK.
