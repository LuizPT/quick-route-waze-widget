package pt.rotarapida.waze;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.net.Uri;
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

    private void updateOne(Context context, AppWidgetManager manager, int id, Bundle options) {
        Bundle size = options != null ? options : manager.getAppWidgetOptions(id);
        int width = size.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, 0);
        int height = size.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, 0);
        boolean wide = WidgetLayoutMode.useWideLayout(width, height);
        RemoteViews views = new RemoteViews(context.getPackageName(),
                wide ? R.layout.widget_wide : R.layout.widget_compact);
        bindAction(context, views, id, WazeAction.HOME, R.id.action_home);
        bindAction(context, views, id, WazeAction.WORK, R.id.action_work);
        bindAction(context, views, id, WazeAction.FAVORITES, R.id.action_favorites);
        bindAction(context, views, id, WazeAction.OPEN_WAZE, R.id.action_open);
        bindAction(context, views, id, WazeAction.SEARCH, R.id.action_search);
        manager.updateAppWidget(id, views);
    }

    private void bindAction(Context context, RemoteViews views, int widgetId,
                            WazeAction action, int viewId) {
        Intent intent = new Intent(context, NavigationActivity.class)
                .putExtra(WazeLauncher.EXTRA_ACTION, action.key())
                .setData(Uri.parse("rotarapida://widget/" + widgetId + "/" + action.key()));
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                action.requestCode() + widgetId * 10, intent, PENDING_FLAGS);
        views.setOnClickPendingIntent(viewId, pendingIntent);
    }
}
