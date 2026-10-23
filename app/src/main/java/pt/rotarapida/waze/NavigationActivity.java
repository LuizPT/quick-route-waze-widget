package pt.rotarapida.waze;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

/** Small transparent trampoline so widget taps can report a missing Waze install. */
public final class NavigationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String key = getIntent().getStringExtra(WazeLauncher.EXTRA_ACTION);
        WazeAction action = WazeAction.fromKey(key);
        if (!WazeLauncher.isInstalled(this)) {
            Intent main = new Intent(this, MainActivity.class)
                    .putExtra(WazeLauncher.EXTRA_SHOW_MISSING, true)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(main);
            finish();
            return;
        }

        try {
            startActivity(WazeLauncher.navigationIntent(this, action));
        } catch (ActivityNotFoundException exception) {
            Intent main = new Intent(this, MainActivity.class)
                    .putExtra(WazeLauncher.EXTRA_SHOW_MISSING, true)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(main);
        }
        finish();
    }
}
