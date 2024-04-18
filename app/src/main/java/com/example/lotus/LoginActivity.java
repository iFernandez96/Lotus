package com.example.lotus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.Login;
import com.example.lotus.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
//    private static final String LOGIN_ACTIVITY_KEY = "LOGIN";
    private String Username;
    private String Password;
    private LoginRepo repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Init the database
        repository = LoginRepo.getRepo(getApplication());
        // Create the binding
        ActivityLoginBinding loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        // Set the content view
        setContentView(loginBinding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Create the binding
        ActivityLoginBinding loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        // Set the click listener for the login button
        loginBinding.loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Username = loginBinding.editTextTextEmailAddress.getText().toString();
                Password = loginBinding.editTextTextPassword.getText().toString();

                insertLoginRecord();
                Intent intent = intentFactory.createIntent(getApplicationContext(), LandingPage.class);
                startActivity(intent);
            }
        });
    }

    private void insertLoginRecord() {
        if (!Objects.equals(Username, "") || !Objects.equals(Password, "")){
            Login log = new Login(Username, Password);
            repository.insertLoginLog(log);
        } else {
            Toast.makeText(this, "Please enter a Username AND a Password...", Toast.LENGTH_SHORT).show();
        }
    }
}