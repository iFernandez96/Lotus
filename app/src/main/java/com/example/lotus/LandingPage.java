package com.example.lotus;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.databinding.ActivityLandingPageBinding;

public class LandingPage extends AppCompatActivity {
    private static final String LOGIN_ACTIVITY_KEY = "LOGIN";
    private LoginRepo repository;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing_page);
        ActivityLandingPageBinding landingPageBinding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(landingPageBinding.getRoot());
        String getUserFromRegister = getIntent().getStringExtra(Constants.USERNAME_REGISTERD);
        landingPageBinding.usernameView.setText(getUserFromRegister);
        repository = LoginRepo.getRepo(getApplication());
        username = getIntent().getStringExtra(LOGIN_ACTIVITY_KEY);


    }

    private boolean checkUserExists(String username){
        if (repository.getUserByUsername(username)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EdgeToEdge.enable(this);
        if (!checkUserExists(username)){
            finish();
        }

    }
}