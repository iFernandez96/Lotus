package com.example.lotus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.RoomSQLiteQuery;

import com.example.lotus.Database.LoginDatabase;
import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.Login;
import com.example.lotus.databinding.ActivityLoginBinding;

import java.util.Objects;

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
                insertLoginRecord();
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

    public static Intent LoginActivityIntentFactory(Context context, boolean receiviedValue){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(LOGIN_ACTIVITY_KEY, receiviedValue);
        return intent;
    }
}