package pt.rotarapida.waze;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

final class WazeLauncher {
    static final String EXTRA_ACTION = "pt.rotarapida.waze.EXTRA_ACTION";
    static final String EXTRA_SHOW_MISSING = "pt.rotarapida.waze.EXTRA_SHOW_MISSING";
    static final String WAZE_PACKAGE = "com.waze";

    private WazeLauncher() { }

    static boolean isInstalled(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                context.getPackageManager().getPackageInfo(WAZE_PACKAGE,
                        PackageManager.PackageInfoFlags.of(0));
            } else {
                context.getPackageManager().getPackageInfo(WAZE_PACKAGE, 0);
            }
            return true;
        } catch (PackageManager.NameNotFoundException exception) {
            return false;
        }
    }

    static Intent navigationIntent(Context context, WazeAction action) {
        String deepLink = action.deepLink();
        if (deepLink != null) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse(deepLink))
                    .setPackage(WAZE_PACKAGE)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        Intent launch = context.getPackageManager().getLaunchIntentForPackage(WAZE_PACKAGE);
        if (launch == null) throw new ActivityNotFoundException("Waze não está instalado");
        launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return launch;
    }
}
