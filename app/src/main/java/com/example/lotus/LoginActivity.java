package com.example.lotus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private static final String LOGIN_ACTIVITY_KEY = "LOGIN";
    private String Username;
    private String Password;
    private LoginRepo repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        repository = LoginRepo.getRepo(getApplication());
        ActivityLoginBinding loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        loginBinding.loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Username = loginBinding.editTextTextEmailAddress.getText().toString();
                Password = loginBinding.editTextTextPassword.getText().toString();
                Toast.makeText(getApplicationContext(),"Login check " + Username + " Password = " + Password, Toast.LENGTH_SHORT).show();
                if (!checkLogin(Username)){
                    Toast.makeText(getApplicationContext(), "Username does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendUserToLandingPage(String username){
        Intent intent = intentFactory.createIntent(getApplicationContext(), LandingPage.class);
        intent.putExtra(LOGIN_ACTIVITY_KEY, username);
        startActivity(intent);
    }

    private  boolean checkLogin(String username){
        if (repository.getUserByUsername(username)){
            sendUserToLandingPage(username);
            return true;
        } else {
            Toast.makeText(this, "Username does not exist", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}