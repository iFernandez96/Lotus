package com.example.lotus;

import android.app.Activity;
import android.content.Context;

public class LotusHeadTracking {

    private final Tracker tracker;
    protected float bottomRange = 180;
    protected float topRange = -180;
    public LotusHeadTracking(Context context, Activity activity) { // Constructor to receive a Context
        // Added to hold the Context
        tracker = new Tracker(context, activity);
        tracker.initializeSensors();
    }

    public void startTracking() {
        tracker.startTracking();
    }

    public void stopTracking() {
        bottomRange = tracker.bottomRange;
        topRange = tracker.topRange;
        tracker.stopTracking();
    }
}
