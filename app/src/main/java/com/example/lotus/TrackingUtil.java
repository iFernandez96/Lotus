package com.example.lotus;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class TrackingUtil {
    public static boolean toggleTracking(Context context) {
        // Implementation to toggle tracking and save state, e.g., using SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean isCurrentlyTracking = prefs.getBoolean("isTracking", false);
        prefs.edit().putBoolean("isTracking", !isCurrentlyTracking).apply();
        return !isCurrentlyTracking;
    }

    public static void playSound(Context context, int soundResourceId) {
        // Play sound logic
        MediaPlayer mediaPlayer = MediaPlayer.create(context, soundResourceId);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}
