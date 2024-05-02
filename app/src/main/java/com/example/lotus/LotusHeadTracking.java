package com.example.lotus;

import android.content.Context;

public class LotusHeadTracking {
    private final Context context; // Added to hold the Context
    private LotusHeadTracking tracker;

    public LotusHeadTracking(Context context) { // Constructor to receive a Context
        this.context = context;
        Tracker tracker = new Tracker(context);
        tracker.initializeSensors();
    }

    public void startTracking() {
        tracker.startTracking();
    }

    public void stopTracking() {
        tracker.stopTracking();
    }
}
