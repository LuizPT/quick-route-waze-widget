# Quick Route 1.2.0 Validation

## Build and interface

- `testDebugUnitTest`, `lint`, and `assembleDebug` passed with Android SDK Platform 37.
- The 1.2.0 release APK was signed with the existing local key and upgraded version 1.1.0 on the Android API 34 emulator.
- The emulator rendered the widget at its wide size and at a simulated 208 × 208 dp compact size. Each of the three action tiles remained square and equal in both previews.
- Portuguese and English were switched in the app. App labels and widget labels changed to the selected language, and the selection remained after restarting the app.
- The Android-rendered widget capture is [widget-cropped.png](screenshots/widget-cropped.png).

## Limits

Waze and Dashline were not installed in the emulator. Navigation into a real Waze account and placement inside Dashline could not be tested. The compact preview simulates widget dimensions with Android `RemoteViews`; the 2 × 2 placement still needs a final check on the target Dashline launcher and screen.

On Android 12 and later, tile size adjusts to the widget dimensions. Older Android versions keep 60 dp square tiles; the minimum widget size is set to fit those tiles in a compact area.

The APK uses the project's existing local signing key. Keep a secure backup of that key to install future updates over this release.
