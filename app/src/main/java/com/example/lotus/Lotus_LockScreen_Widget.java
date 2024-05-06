package com.example.lotus;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ImageButton;
import android.widget.RemoteViews;

import com.example.lotus.databinding.LotusLockWidgetBinding;

public class Lotus_LockScreen_Widget extends AppWidgetProvider {
    private LotusHeadTracking lotusHeadTracking;
    private LotusLockWidgetBinding binding;

    public static final String TOGGLE_TRACKING_ACTION = "com.example.lotus.TOGGLE_TRACKING";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context, TrackingToggleReceiver.class);
        intent.setAction("com.example.lotus.TOGGLE_TRACKING");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.lotus_lock_widget);
        views.setOnClickPendingIntent(R.id.imageButton, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (TOGGLE_TRACKING_ACTION.equals(intent.getAction())) {
            // Toggle tracking functionality
            toggleTracking(context);
        }
    }

    private void toggleTracking(Context context) {
        // Implement toggling logic, possibly by using a service or shared preferences
        // This example assumes you're using a static utility method or service to manage tracking
        boolean isTracking = TrackingUtil.toggleTracking(context); // This method toggles and returns the new state
        if (isTracking) {
            TrackingUtil.playSound(context, R.raw.autoon);
        } else {
            TrackingUtil.playSound(context, R.raw.autooff);
        }
    }
}
