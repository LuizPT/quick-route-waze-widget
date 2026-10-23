# Quick Route 1.1.0 Validation

## Build and tests

- `testDebugUnitTest`: passed; covers Home and Work links, five unique actions, and widget layout selection.
- `lint`: passed with no issues.
- `assembleDebug` and `assembleRelease`: passed with Android SDK Platform 37.
- The signed 1.1.0 APK replaced version 1.0.1 and launched on an Android API 34 emulator at 1920 × 720 landscape; the package reports `versionCode 3` and `versionName 1.1.0`.
- Android Package Manager recognized `QuickActionsWidgetProvider` as a home screen widget provider.
- Compact and wide layouts were rendered and visually inspected in the emulator. Both show all five shortcuts with centered icons and labels, consistent cards, and the cyan route mark.
- Cropped capture of the widget `RemoteViews`: [widget-cropped.png](screenshots/widget-cropped.png).

## Test limitations

Waze and Dashline were not installed in the emulator. A real navigation launch, adding the widget inside Dashline, and checking saved destinations in a Waze account could not be tested. The image shows the Android-rendered widget layout; it does not show the widget placed inside Dashline.

The distributed APK is signed with a local installation key. The private key is stored outside the project at `%LOCALAPPDATA%\RotaRapidaSigning` and is not included in the source archive. Keep a secure backup of this key to publish updates that can replace this installation.
