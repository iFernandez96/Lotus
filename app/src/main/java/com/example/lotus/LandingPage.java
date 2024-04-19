package com.example.lotus;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;

public class LandingPage extends AppCompatActivity {
    private LoginRepo repository;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing_page);
        repository = LoginRepo.getRepo(getApplication());
        username = getIntent().getStringExtra(Constants.LOGIN_ACTIVITY_KEY);
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
}