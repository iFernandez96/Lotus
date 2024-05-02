package com.example.lotus;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.User;
import com.example.lotus.databinding.ActivityLandingPageBinding;

public class LandingPage extends AppCompatActivity {
    private LoginRepo repository;
    private String username;
    private boolean isTracking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing_page);
    }


    /**
     * Check if the user exists in the database
     * @param username
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

        username = getIntent().getStringExtra(Constants.LOGIN_ACTIVITY_KEY);
        if (!checkUserExists(username)){
            finish();
        }
        landingPageBinding.usernameView.setText(username);


        User user = repository.getUserByUsername(username);
        if (user != null) {
            landingPageBinding.usernameView.setText(username);
            landingPageBinding.isAdmin.setVisibility(user.isAdmin() ? View.VISIBLE : View.GONE);
        }

        ImageButton lotusHeadTrackingbutton = findViewById(R.id.imageButton);
        LotusHeadTracking lotusHeadTracking = new LotusHeadTracking(getApplicationContext());
        lotusHeadTrackingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTracking = !isTracking;
                if (isTracking) {
                    //TODO: Add autoon sound
                    //playSound(R.raw.autoon);
                    lotusHeadTracking.startTracking();
                } else {
                    playSound(R.raw.autooff);
                    lotusHeadTracking.stopTracking();
                }
            }
        });

        Button settingsButton = landingPageBinding.settingsButton;

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Done with basic startup tasks

    }

    private void playSound(int soundResourceId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, soundResourceId);
        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        } else {
            // Handle error: MediaPlayer creation failed
            Log.e("MediaPlayer", "Could not create MediaPlayer for resource ID: " + soundResourceId);
        }
    }

}