package com.example.lotus;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.Statistics;
import com.example.lotus.Database.entities.User;
import com.example.lotus.databinding.ActivityLandingPageBinding;

import java.time.LocalDateTime;

public class LandingPage extends AppCompatActivity {
    private LoginRepo repository;
    private boolean isTracking = false;
    // private static final String TAG = "LandingPage";
    private Statistics statistics;
    private final long totalTrackerUseTime = 0;
    private LandingPage activity;
    private LotusHeadTracking lotusHeadTracking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing_page);
        activity = this;
    }


    /**
     * Check if the user exists in the database
     * @return true if the user exists, false otherwise
     */
    private boolean checkUserExists(String username){
        return repository.getUserByUsername(username) != null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EdgeToEdge.enable(this);

        ActivityLandingPageBinding landingPageBinding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(landingPageBinding.getRoot());

        repository = LoginRepo.getRepo(getApplication());
        assert repository != null;

        String username = getIntent().getStringExtra(Constants.LOGIN_ACTIVITY_KEY);
        if (!checkUserExists(username)){
            Toast.makeText(this, "Please Refresh cache and delete App data", Toast.LENGTH_SHORT).show();
            finish();
        }


        landingPageBinding.usernameView.setText(username);
        User user = repository.getUserByUsername(username);
        if (user != null) {
            // Update the statistics for the user
            statistics = repository.getStatisticsByUserID(user.getId());
            statistics.setLastLogin(LocalDateTime.now());
            statistics.setTotalLogins(statistics.getTotalLogins() + 1);

            landingPageBinding.usernameView.setText(username);
            landingPageBinding.isAdmin.setVisibility(user.isAdmin() ? View.VISIBLE : View.GONE);
        }

        ImageButton lotusHeadTrackingbutton = findViewById(R.id.imageButton);
        lotusHeadTracking = new LotusHeadTracking(getApplicationContext(), this);
        lotusHeadTrackingbutton.setOnClickListener(v -> {
            isTracking = !isTracking;
            new LotusHeadTrackingHelper(isTracking, statistics, totalTrackerUseTime, lotusHeadTracking, activity).execute();
        });

        Button settingsButton = landingPageBinding.settingsButton;

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPage.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Done with basic startup tasks

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanUp();
    }

    private void cleanUp() {
        if (isTracking)
        {
            playSound(R.raw.autooff);
            lotusHeadTracking.stopTracking();
            statistics.setLastLogout(LocalDateTime.now());
            statistics.setLastTrackerUse(LocalDateTime.now());
            repository.updateAllUserStatistics(statistics.getUserID(), statistics.getRangeHeadMovement(), statistics.getTotalLogins(), statistics.getTotalHeadTriggers(),statistics.getTotalTimesUsedTracker(), statistics.getAverageUseTime(), statistics.getLastLogin(), statistics.getLastLogout(), statistics.getLastTrackerUse());
        }
    }

    void playSound(int soundResourceId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, soundResourceId);
        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        } else {
            // Handle error: MediaPlayer creation failed
            Log.e("MediaPlayer", "Could not create MediaPlayer for resource ID: " + soundResourceId);
        }
    }

}