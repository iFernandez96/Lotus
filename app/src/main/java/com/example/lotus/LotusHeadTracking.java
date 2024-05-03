package com.example.lotus;

import android.app.Activity;
import android.content.Context;

public class LotusHeadTracking {
    private final Context context; // Added to hold the Context

    private Tracker tracker;
    public LotusHeadTracking(Context context, Activity activity) { // Constructor to receive a Context
        this.context = context;
        tracker = new Tracker(context, activity);
        tracker.initializeSensors();
    }

    public void startTracking() {
        tracker.startTracking();
    }

    public void stopTracking() {
        tracker.stopTracking();
    }
}
