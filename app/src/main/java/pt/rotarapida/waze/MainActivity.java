package pt.rotarapida.waze;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public final class MainActivity extends Activity {
    private static final int BG = Color.rgb(10, 15, 21);
    private static final int SURFACE = Color.rgb(18, 27, 38);
    private static final int SURFACE_HI = Color.rgb(25, 37, 51);
    private static final int STROKE = Color.rgb(42, 57, 72);
    private static final int TEXT = Color.rgb(241, 246, 250);
    private static final int MUTED = Color.rgb(153, 170, 186);
    private static final int MINT = Color.rgb(51, 204, 255);
    private static final int AMBER = Color.rgb(255, 190, 131);
    private TextView status;
    private TextView statusDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureSystemBars();
        setContentView(buildScreen());
        updateStatus(getIntent() != null && getIntent().getBooleanExtra(
                WazeLauncher.EXTRA_SHOW_MISSING, false));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        updateStatus(intent.getBooleanExtra(WazeLauncher.EXTRA_SHOW_MISSING, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (status != null) updateStatus(false);
    }

    private void configureSystemBars() {
        Window window = getWindow();
        window.setStatusBarColor(BG);
        window.setNavigationBarColor(BG);
        if (Build.VERSION.SDK_INT >= 29) window.setNavigationBarContrastEnforced(false);
        if (Build.VERSION.SDK_INT >= 35) {
            View decor = window.getDecorView();
            decor.setOnApplyWindowInsetsListener((view, insets) -> {
                android.graphics.Insets bars = insets.getInsets(
                        WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                view.setPadding(0, bars.top, 0, bars.bottom);
                return insets;
            });
        }
    }

    private View buildScreen() {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(BG);
        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(22), dp(18), dp(22), dp(18));
        scroll.addView(page, new ScrollView.LayoutParams(-1, -2));

        page.addView(buildHeader(), new LinearLayout.LayoutParams(-1, dp(70)));
        View divider = new View(this);
        divider.setBackgroundColor(STROKE);
        LinearLayout.LayoutParams dividerLp = new LinearLayout.LayoutParams(-1, dp(1));
        dividerLp.bottomMargin = dp(16);
        page.addView(divider, dividerLp);

        boolean landscapeWide = getResources().getConfiguration().orientation == 2
                && getResources().getDisplayMetrics().widthPixels
                / getResources().getDisplayMetrics().density >= 720;
        if (landscapeWide) {
            LinearLayout body = new LinearLayout(this);
            body.setOrientation(LinearLayout.HORIZONTAL);
            body.setGravity(Gravity.TOP);
            page.addView(body, new LinearLayout.LayoutParams(-1, -2));
            LinearLayout left = buildIntroPanel();
            LinearLayout right = buildActionsPanel();
            body.addView(left, new LinearLayout.LayoutParams(0, -2, 0.43f));
            LinearLayout.LayoutParams rightLp = new LinearLayout.LayoutParams(0, -2, 0.57f);
            rightLp.leftMargin = dp(16);
            body.addView(right, rightLp);
        } else {
            page.addView(buildIntroPanel(), new LinearLayout.LayoutParams(-1, -2));
            LinearLayout.LayoutParams actionsLp = new LinearLayout.LayoutParams(-1, -2);
            actionsLp.topMargin = dp(14);
            page.addView(buildActionsPanel(), actionsLp);
        }
        TextView footer = text(getString(R.string.independent_notice), 11, MUTED, false);
        footer.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams footerLp = new LinearLayout.LayoutParams(-1, dp(34));
        footerLp.topMargin = dp(8);
        page.addView(footer, footerLp);
        return scroll;
    }

    private View buildHeader() {
        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        android.widget.ImageView mark = new android.widget.ImageView(this);
        mark.setBackground(makeShape(Color.rgb(22, 55, 71), Color.rgb(42, 70, 91), 17));
        mark.setImageResource(R.drawable.ic_route_mark);
        mark.setPadding(dp(10), dp(10), dp(10), dp(10));
        header.addView(mark, new LinearLayout.LayoutParams(dp(48), dp(48)));
        LinearLayout names = new LinearLayout(this);
        names.setOrientation(LinearLayout.VERTICAL);
        names.setPadding(dp(14), 0, 0, 0);
        names.addView(text(getString(R.string.app_name), 21, TEXT, true));
        names.addView(text(getString(R.string.header_subtitle), 12, MUTED, false));
        header.addView(names, new LinearLayout.LayoutParams(0, -2, 1));
        TextView version = text("V1.1.0", 11, MINT, true);
        version.setGravity(Gravity.CENTER);
        version.setBackground(makeShape(0xff163747, 0xff29465b, 30));
        version.setPadding(dp(12), dp(6), dp(12), dp(6));
        header.addView(version);
        return header;
    }

    private LinearLayout buildIntroPanel() {
        LinearLayout card = card();
        card.setPadding(dp(18), dp(16), dp(18), dp(16));
        TextView eyebrow = text(getString(R.string.intro_eyebrow), 10, MINT, true);
        card.addView(eyebrow);
        TextView title = text(getString(R.string.intro_title), 19, TEXT, true);
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(-1, -2);
        titleLp.topMargin = dp(6);
        card.addView(title, titleLp);
        TextView copy = text(getString(R.string.intro_body), 13, MUTED, false);
        copy.setLineSpacing(dp(3), 1f);
        LinearLayout.LayoutParams copyLp = new LinearLayout.LayoutParams(-1, -2);
        copyLp.topMargin = dp(5);
        card.addView(copy, copyLp);

        LinearLayout statusLine = new LinearLayout(this);
        statusLine.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams statusLp = new LinearLayout.LayoutParams(-1, -2);
        statusLp.topMargin = dp(12);
        card.addView(statusLine, statusLp);
        View dot = new View(this);
        dot.setBackground(makeShape(MINT, Color.TRANSPARENT, 20));
        statusLine.addView(dot, new LinearLayout.LayoutParams(dp(8), dp(8)));
        status = text("", 12, TEXT, true);
        LinearLayout.LayoutParams statusTextLp = new LinearLayout.LayoutParams(-1, -2);
        statusTextLp.leftMargin = dp(8);
        statusLine.addView(status, statusTextLp);
        statusDetail = text("", 11, MUTED, false);
        statusDetail.setLineSpacing(dp(2), 1f);
        LinearLayout.LayoutParams detailLp = new LinearLayout.LayoutParams(-1, -2);
        detailLp.topMargin = dp(4);
        card.addView(statusDetail, detailLp);
        return card;
    }

    private LinearLayout buildActionsPanel() {
        LinearLayout column = new LinearLayout(this);
        column.setOrientation(LinearLayout.VERTICAL);
        column.addView(sectionLabel(getString(R.string.destinations_heading)));
        LinearLayout destinations = new LinearLayout(this);
        destinations.setOrientation(LinearLayout.HORIZONTAL);
        column.addView(destinations, new LinearLayout.LayoutParams(-1, dp(86)));
        destinations.addView(actionButton(WazeAction.HOME, R.drawable.ic_home, "Home", MINT, false),
                new LinearLayout.LayoutParams(0, -1, 1));
        LinearLayout.LayoutParams workLp = new LinearLayout.LayoutParams(0, -1, 1);
        workLp.leftMargin = dp(10);
        destinations.addView(actionButton(WazeAction.WORK, R.drawable.ic_work, "Work", MINT, false), workLp);

        LinearLayout.LayoutParams noteLp = new LinearLayout.LayoutParams(-1, -2);
        noteLp.topMargin = dp(7);
        TextView favoriteNote = text(getString(R.string.favorite_note), 11, MUTED, false);
        favoriteNote.setLineSpacing(dp(2), 1f);
        column.addView(favoriteNote, noteLp);

        LinearLayout.LayoutParams favoritesLp = new LinearLayout.LayoutParams(-1, dp(62));
        favoritesLp.topMargin = dp(9);
        column.addView(actionButton(WazeAction.FAVORITES, R.drawable.ic_favorites,
                "Waze Favorites", MINT, false), favoritesLp);

        TextView toolsLabel = sectionLabel(getString(R.string.waze_heading));
        LinearLayout.LayoutParams toolsHeadingLp = new LinearLayout.LayoutParams(-1, dp(28));
        toolsHeadingLp.topMargin = dp(12);
        column.addView(toolsLabel, toolsHeadingLp);
        LinearLayout utilities = new LinearLayout(this);
        utilities.setOrientation(LinearLayout.HORIZONTAL);
        column.addView(utilities, new LinearLayout.LayoutParams(-1, dp(72)));
        utilities.addView(actionButton(WazeAction.OPEN_WAZE, R.drawable.ic_open, "Open Waze", MINT, true),
                new LinearLayout.LayoutParams(0, -1, 1));
        LinearLayout.LayoutParams searchLp = new LinearLayout.LayoutParams(0, -1, 1);
        searchLp.leftMargin = dp(10);
        utilities.addView(actionButton(WazeAction.SEARCH, R.drawable.ic_search, "Search", MINT, false), searchLp);
        return column;
    }

    private TextView sectionLabel(String value) {
        TextView label = text(value, 11, MUTED, true);
        label.setGravity(Gravity.CENTER_VERTICAL);
        return label;
    }

    private View actionButton(WazeAction action, int iconResource, String label, int iconColor,
                              boolean primary) {
        LinearLayout button = new LinearLayout(this);
        button.setOrientation(LinearLayout.HORIZONTAL);
        button.setGravity(Gravity.CENTER);
        button.setPadding(dp(10), dp(8), dp(10), dp(8));
        button.setBackground(makeShape(primary ? 0xff163747 : SURFACE_HI,
                primary ? 0xff2a7897 : STROKE, 15));
        android.widget.ImageView icon = new android.widget.ImageView(this);
        icon.setImageResource(iconResource);
        icon.setColorFilter(iconColor);
        button.addView(icon, new LinearLayout.LayoutParams(dp(30), dp(30)));
        TextView name = text(label, 14, primary ? MINT : TEXT, true);
        name.setGravity(Gravity.CENTER_VERTICAL);
        name.setMaxLines(1);
        LinearLayout.LayoutParams nameLp = new LinearLayout.LayoutParams(-2, -1);
        nameLp.leftMargin = dp(5);
        button.addView(name, nameLp);
        button.setClickable(true);
        button.setFocusable(true);
        if (Build.VERSION.SDK_INT >= 23) {
            button.setForeground(getDrawableCompat(android.R.drawable.list_selector_background));
        }
        button.setOnClickListener(view -> performAction(action));
        return button;
    }

    private void performAction(WazeAction action) {
        if (!WazeLauncher.isInstalled(this)) {
            updateStatus(true);
            Toast.makeText(this, R.string.waze_missing_toast, Toast.LENGTH_LONG).show();
            return;
        }
        if (action == WazeAction.SEARCH) {
            Toast.makeText(this, R.string.search_hint, Toast.LENGTH_LONG).show();
        } else if (action == WazeAction.FAVORITES) {
            Toast.makeText(this, R.string.favorites_hint, Toast.LENGTH_LONG).show();
        }
        try {
            startActivity(WazeLauncher.navigationIntent(this, action));
        } catch (ActivityNotFoundException exception) {
            updateStatus(true);
            Toast.makeText(this, R.string.waze_missing_toast, Toast.LENGTH_LONG).show();
        }
    }

    private void updateStatus(boolean showMissingMessage) {
        if (status == null || statusDetail == null) return;
        if (WazeLauncher.isInstalled(this)) {
            status.setText(R.string.waze_ready);
            status.setTextColor(MINT);
            statusDetail.setText(showMissingMessage
                    ? R.string.waze_routing_issue : R.string.favorite_note);
        } else {
            status.setText(R.string.waze_missing);
            status.setTextColor(AMBER);
            statusDetail.setText(R.string.waze_missing_detail);
        }
    }

    private LinearLayout card() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackground(makeShape(SURFACE, STROKE, 18));
        return card;
    }

    private TextView text(String value, int sizeSp, int color, boolean bold) {
        TextView text = new TextView(this);
        text.setText(value);
        text.setTextSize(sizeSp);
        text.setTextColor(color);
        text.setTypeface(Typeface.create("sans-serif", bold ? Typeface.BOLD : Typeface.NORMAL));
        text.setIncludeFontPadding(false);
        return text;
    }

    private GradientDrawable makeShape(int fill, int stroke, int radiusDp) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(fill);
        shape.setCornerRadius(dp(radiusDp));
        if (stroke != Color.TRANSPARENT) shape.setStroke(dp(1), stroke);
        return shape;
    }

    private android.graphics.drawable.Drawable getDrawableCompat(int id) {
        return getDrawable(id);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
