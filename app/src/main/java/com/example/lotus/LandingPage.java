package com.example.lotus;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.databinding.ActivityLandingPageBinding;

public class LandingPage extends AppCompatActivity {
    private LoginRepo repository;
    private String username;
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
        Button button = landingPageBinding.LogoutButton;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }
    private void showLogoutDialogue(){
        AlertDialog.Builder alert = new AlertDialog.Builder(LandingPage.this);
        final AlertDialog alertTalk = alert.create();

        alert.setTitle("Logout?");

        alert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertTalk.dismiss();
            }
        });
        alert.create().show();
    }

    private void logout(){
        startActivity(intentFactory.createIntent(getApplicationContext(),LoginActivity.class));
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