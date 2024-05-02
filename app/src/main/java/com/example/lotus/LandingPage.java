package com.example.lotus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.User;
import com.example.lotus.databinding.ActivityLandingPageBinding;

public class LandingPage extends AppCompatActivity {
    private LoginRepo repository;
    private String username;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing_page);
        ActivityLandingPageBinding landingPageBinding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(landingPageBinding.getRoot());
        username = getIntent().getStringExtra(Constants.LOGIN_ACTIVITY_KEY);
        landingPageBinding.usernameView.setText(username);
        repository = LoginRepo.getRepo(getApplication());
        User user = repository.getUserByUsername(username);
        if (user != null) {
            landingPageBinding.usernameView.setText(username);
            landingPageBinding.isAdmin.setVisibility(user.isAdmin() ? View.VISIBLE : View.GONE);
        }

        /*

        playSound(R.raw.autooff);

         */
        Button settingsButton = landingPageBinding.settingsButton;

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }


    private boolean checkUserExists(String username){
        return repository.getUserByUsername(username) != null;
    }
    @Override
    protected void onStart() {
        super.onStart();
        EdgeToEdge.enable(this);
        if (!checkUserExists(username)){
            finish();
        }

    }

    private void playSound(int soundResourceId){

        mediaPlayer = MediaPlayer.create(this,soundResourceId);
        mediaPlayer.start();

        //mediaPlayer.setOnCompletionListener();
    }
}