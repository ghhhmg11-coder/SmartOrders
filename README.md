# Smart Orders — نظام إدارة الطلبات الذكي

A native Android application built with Kotlin.

## Features

- **Login Screen** — Arabic RTL UI with credentials: `admin` / `1234`
- **Floating Overlay Button** — Draggable button that appears above all apps (requires SYSTEM_ALERT_WINDOW permission)
- **Accessibility Service** — Monitors window and click events across the device
- **Full Arabic RTL Support** — All strings in Arabic, layout direction set to RTL

## Requirements

- Android SDK 34 (target)
- Android SDK 24 (minimum — Android 7.0)
- JDK 17
- Gradle 8.4

## Build

### Local build (Android Studio)
1. Open the project in Android Studio Hedgehog or later
2. Click **Build → Build Bundle(s) / APK(s) → Build APK(s)**

### Command line
```bash
chmod +x gradlew
./gradlew assembleDebug
```
APK output: `app/build/outputs/apk/debug/app-debug.apk`

### GitHub Actions (automatic)
Push to `main` or `master` branch — the workflow in `.github/workflows/build-apk.yml` runs automatically and uploads the APK as a build artifact.

## Permissions Required

| Permission | Purpose |
|---|---|
| `SYSTEM_ALERT_WINDOW` | Draw floating button over other apps |
| `FOREGROUND_SERVICE` | Keep overlay service alive |
| `BIND_ACCESSIBILITY_SERVICE` | Monitor screen content |

## Package

`com.smartorders`

## Architecture

- `LoginActivity` — Entry point, validates credentials
- `MainActivity` — Dashboard with overlay and accessibility controls
- `OverlayService` — Foreground service managing the floating FAB
- `SmartOrdersAccessibilityService` — Handles accessibility events
