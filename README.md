# Quick Route

An Android home screen widget with one tap shortcuts for Home, Work, Favorites, opening Waze, and search. Works with Android launchers and is designed for Dashline's customizable home screen.

## Widget

![Quick Route Waze widget in wide layout](docs/screenshots/widget-cropped.png)

The widget uses aligned icons and labels, consistent dark cards, and a cyan route mark across the widget, app, and Android launcher icon.

## Install

1. Download the [Quick Route 1.1.0 APK](https://github.com/LuizPT/quick-route-waze-widget/releases/tag/v1.1.0) and open Quick Route once.
2. Install Waze. Home and Work shortcuts use the saved destinations in your Waze account.
3. In Dashline, open **Settings → Customize home screen**, add a widget, and choose **Quick Route**. Resize it to show all five shortcuts.

The widget has a compact layout with shortcuts in rows and a wide layout with all five shortcuts in one row. It can also be added from the widget picker in other Android launchers.

## Favorites and search

**Favorites** opens Waze and prompts you to open **Saved**. Waze does not provide a public deep link to the complete favorites list. Home and Work open their saved Waze destinations directly for navigation.

**Search** opens Waze and prompts you to tap the search icon. Quick Route does not rely on an undocumented search URL. It does not read or store destinations and does not request location, network, or other permissions.

## Build and validate

Requires JDK 17 or later and Android SDK Platform 37. On Windows, use the included Gradle Wrapper:

```powershell
.\gradlew.bat testDebugUnitTest lint assembleDebug assembleRelease
```

The locally built APK is at `app/build/outputs/apk/release/app-release.apk`. The signed installable APK is available from the [GitHub release](https://github.com/LuizPT/quick-route-waze-widget/releases/tag/v1.1.0). Production code and tests are in `app/src/`. To sign a local build, set `ROTA_RAPIDA_KEYSTORE`, `ROTA_RAPIDA_STORE_PASSWORD`, `ROTA_RAPIDA_KEY_ALIAS`, and `ROTA_RAPIDA_KEY_PASSWORD` in the environment. The signing key stays outside the repository at `%LOCALAPPDATA%\RotaRapidaSigning`; its password is protected for the current Windows account. The private key is not included in the source archive.

See [docs/VALIDATION.md](docs/VALIDATION.md) for build, installation, and emulator results. Widget screenshots are in `docs/screenshots/`.
