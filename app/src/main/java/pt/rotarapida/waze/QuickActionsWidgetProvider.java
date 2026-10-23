package pt.rotarapida.waze;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.content.res.Configuration;
import android.net.Uri;
import android.util.TypedValue;
import android.widget.RemoteViews;

public final class QuickActionsWidgetProvider extends AppWidgetProvider {
    private static final int PENDING_FLAGS = PendingIntent.FLAG_UPDATE_CURRENT
            | (Build.VERSION.SDK_INT >= 23 ? PendingIntent.FLAG_IMMUTABLE : 0);

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] ids) {
        for (int id : ids) updateOne(context, manager, id, null);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager manager,
                                          int appWidgetId, Bundle newOptions) {
        updateOne(context, manager, appWidgetId, newOptions);
    }

    public static void refreshAll(Context context) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] ids = manager.getAppWidgetIds(
                new android.content.ComponentName(context, QuickActionsWidgetProvider.class));
        if (ids.length > 0) new QuickActionsWidgetProvider().onUpdate(context, manager, ids);
    }

    private void updateOne(Context context, AppWidgetManager manager, int id, Bundle options) {
        Bundle size = options != null ? options : manager.getAppWidgetOptions(id);
        boolean landscape = context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        int width = size.getInt(landscape ? AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH
                : AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, 0);
        int height = size.getInt(landscape ? AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT
                : AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, 0);
        if (width <= 0) width = size.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, 0);
        if (height <= 0) height = size.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, 0);
        manager.updateAppWidget(id, createRemoteViews(context, id, width, height));
    }

    public static RemoteViews createRemoteViews(Context context, int widgetId,
                                                int widthDp, int heightDp) {
        Context localized = AppLanguage.wrap(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_wide);
        views.setTextViewText(R.id.label_home, localized.getString(R.string.widget_home));
        views.setTextViewText(R.id.label_work, localized.getString(R.string.widget_work));
        views.setTextViewText(R.id.label_open, localized.getString(R.string.widget_open));
        views.setContentDescription(R.id.action_home, localized.getString(R.string.widget_home));
        views.setContentDescription(R.id.action_work, localized.getString(R.string.widget_work));
        views.setContentDescription(R.id.action_open, localized.getString(R.string.widget_open));
        if (Build.VERSION.SDK_INT >= 31) {
            int side = WidgetLayoutMode.squareTileSideDp(widthDp, heightDp);
            if (side > 0) {
                int rowWidth = WidgetLayoutMode.actionRowWidthDp(side);
                views.setViewLayoutWidth(R.id.action_row, rowWidth, TypedValue.COMPLEX_UNIT_DIP);
                views.setViewLayoutHeight(R.id.action_row, side, TypedValue.COMPLEX_UNIT_DIP);
                views.setViewLayoutWidth(R.id.action_home, side, TypedValue.COMPLEX_UNIT_DIP);
                views.setViewLayoutHeight(R.id.action_home, side, TypedValue.COMPLEX_UNIT_DIP);
                views.setViewLayoutWidth(R.id.action_work, side, TypedValue.COMPLEX_UNIT_DIP);
                views.setViewLayoutHeight(R.id.action_work, side, TypedValue.COMPLEX_UNIT_DIP);
                views.setViewLayoutWidth(R.id.action_open, side, TypedValue.COMPLEX_UNIT_DIP);
                views.setViewLayoutHeight(R.id.action_open, side, TypedValue.COMPLEX_UNIT_DIP);
                int iconSide = Math.min(32, Math.max(20, side / 3));
                setIconSize(views, R.id.icon_home, iconSide);
                setIconSize(views, R.id.icon_work, iconSide);
                setIconSize(views, R.id.icon_open, iconSide);
                float labelSize = side < 72 ? 10f : 11f;
                views.setTextViewTextSize(R.id.label_home, TypedValue.COMPLEX_UNIT_SP, labelSize);
                views.setTextViewTextSize(R.id.label_work, TypedValue.COMPLEX_UNIT_SP, labelSize);
                views.setTextViewTextSize(R.id.label_open, TypedValue.COMPLEX_UNIT_SP, labelSize);
            }
        }
        bindAction(context, views, widgetId, WazeAction.HOME, R.id.action_home);
        bindAction(context, views, widgetId, WazeAction.WORK, R.id.action_work);
        bindAction(context, views, widgetId, WazeAction.OPEN_WAZE, R.id.action_open);
        return views;
    }

    @android.annotation.TargetApi(31)
    private static void setIconSize(RemoteViews views, int viewId, int sideDp) {
        views.setViewLayoutWidth(viewId, sideDp, TypedValue.COMPLEX_UNIT_DIP);
        views.setViewLayoutHeight(viewId, sideDp, TypedValue.COMPLEX_UNIT_DIP);
    }

    private static void bindAction(Context context, RemoteViews views, int widgetId,
                                   WazeAction action, int viewId) {
        Intent intent = new Intent(context, NavigationActivity.class)
                .putExtra(WazeLauncher.EXTRA_ACTION, action.key())
                .setData(Uri.parse("rotarapida://widget/" + widgetId + "/" + action.key()));
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                action.requestCode() + widgetId * 10, intent, PENDING_FLAGS);
        views.setOnClickPendingIntent(viewId, pendingIntent);
    }
}
