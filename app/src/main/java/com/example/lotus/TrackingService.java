package com.example.lotus;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;

import com.example.lotus.Database.entities.Statistics;


public class TrackingService extends Service {
    public static final String ACTION_TOGGLE_TRACKING = "com.example.lotus.ACTION_TOGGLE_TRACKING";
    public static final String EXTRA_IS_TRACKING = "com.example.lotus.EXTRA_IS_TRACKING";
    public static final String EXTRA_USER_ID = "com.example.lotus.EXTRA_USER_ID";

    private void handleTracking(boolean isTracking, int userID) {
        // Retrieve necessary data like statistics and create LotusHeadTracking instance
        // Assume these are initialized here or passed suitably
        Statistics statistics = new Statistics(userID); // Properly initialized
        LotusHeadTracking lotusHeadTracking = new LotusHeadTracking(getApplicationContext(), (Activity) getApplicationContext());
        //otusHeadTrackingHelper helper = new LotusHeadTrackingHelper(isTracking, statistics, 0, lotusHeadTracking, getApplicationContext());
        //helper.execute();
    }

    @NonNull
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            boolean isTracking = intent.getBooleanExtra(EXTRA_IS_TRACKING, false);
            int userId = intent.getIntExtra(EXTRA_USER_ID, 0); // Retrieve user ID from intent
            handleTracking(isTracking, userId);
        }
        return START_NOT_STICKY;
    }
}
