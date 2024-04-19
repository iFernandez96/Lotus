package com.example.lotus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.User;

import java.util.Objects;

public class CreateUserActivity extends AppCompatActivity {
    private static final String LOGIN_ACTIVITY_KEY = "LOGIN";
    private String username;
    private String password;
    private LoginRepo repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_user);
        repository = LoginRepo.getRepo(getApplication());
    }
    @Override
    protected void onStart() {
        super.onStart();
        Button button = findViewById(R.id.button2);
    }

    private void insertLoginRecord(String Username, String Password) {
        if (checkLogin(Username)) {
            Toast.makeText(this, "The User already exists. Please enter a new Username", Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO: This needs to be in Register New User Activity...

        if (!Objects.equals(Username, "") || !Objects.equals(Password, "")) {
            User user = new User(Username, Password);
            repository.insertUser2Database(user);
        } else {
            Toast.makeText(this, "Please enter a Username AND a Password...", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean checkLogin(String username){
        if (repository.getUserByUsername(username)){
            return true;
        } else {
            Toast.makeText(this, "Username does not exist", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}