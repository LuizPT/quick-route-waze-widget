package pt.rotarapida.waze;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RemoteViews;

import java.io.IOException;
import java.io.OutputStream;

/** Debug-only preview for capturing the actual widget RemoteViews layout. */
public final class WidgetPreviewActivity extends Activity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        Window window = getWindow();
        window.setStatusBarColor(0xff0a0f15);
        window.setNavigationBarColor(0xff0a0f15);

        FrameLayout screen = new FrameLayout(this);
        screen.setBackgroundColor(0xff0a0f15);
        setContentView(screen);

        FrameLayout host = new FrameLayout(this);
        int sideDp = 20;
        int side = dp(sideDp);
        int screenWidthDp = Math.round(getResources().getDisplayMetrics().widthPixels
                / getResources().getDisplayMetrics().density);
        int screenHeightDp = Math.round(getResources().getDisplayMetrics().heightPixels
                / getResources().getDisplayMetrics().density);
        int hostWidthDp = getIntent().getIntExtra("widget_width_dp",
                Math.max(288, screenWidthDp - sideDp * 2));
        int hostHeightDp = getIntent().getIntExtra("widget_height_dp",
                Math.min(hostWidthDp / 3, screenHeightDp - 48));
        FrameLayout.LayoutParams hostParams = new FrameLayout.LayoutParams(
                dp(hostWidthDp), dp(hostHeightDp), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        hostParams.setMargins(side, 0, side, dp(24));
        screen.addView(host, hostParams);

        RemoteViews widget = QuickActionsWidgetProvider.createRemoteViews(
                this, 0, hostWidthDp, hostHeightDp);
        View rendered = widget.apply(this, host);
        host.addView(rendered, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        String captureName = getIntent().getStringExtra("capture_name");
        rendered.post(() -> saveWidgetImage(rendered, captureName));
    }

    private void saveWidgetImage(View widget, String captureName) {
        if (Build.VERSION.SDK_INT < 29 || widget.getWidth() == 0 || widget.getHeight() == 0) return;
        Bitmap bitmap = Bitmap.createBitmap(widget.getWidth(), widget.getHeight(),
                Bitmap.Config.ARGB_8888);
        widget.draw(new Canvas(bitmap));

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, captureName == null
                ? "Quick-Route-widget-cropped.png" : captureName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/QuickRoute");
        Uri image = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (image == null) return;
        try (OutputStream output = getContentResolver().openOutputStream(image)) {
            if (output != null) bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        } catch (IOException ignored) {
            getContentResolver().delete(image, null, null);
        } finally {
            bitmap.recycle();
        }
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
