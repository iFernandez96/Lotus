package com.example.lotus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.User;
import com.example.lotus.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private String Username;
    private String Password;
    private LoginRepo repository;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        repository = LoginRepo.getRepo(getApplication());
        ActivityLoginBinding loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        String usernameFromRegister = getIntent().getStringExtra(Constants.USERNAME_REGISTERD);
        loginBinding.editTextTextEmailAddress.setText(usernameFromRegister);
        loginBinding.loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Username = loginBinding.editTextTextEmailAddress.getText().toString();
                Password = loginBinding.editTextTextPassword.getText().toString();
                Toast.makeText(getApplicationContext(),"Login check " + Username + " Password = " + Password, Toast.LENGTH_SHORT).show();
                user = repository.getUserByUsername(Username);
                checkLogin(user);
            }
        });
        loginBinding.registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = intentFactory.createIntent(getApplicationContext(),CreateUserActivity.class);
                startActivity(i);
            }
        });
    }

    private void sendUserToLandingPage(User user){
        saveLoginSession(getApplicationContext(), user);
        Intent intent = intentFactory.createIntent(getApplicationContext(), LandingPage.class);
        intent.putExtra(Constants.LOGIN_ACTIVITY_KEY, user.getUsername());
        startActivity(intent);
    }

    private void checkLogin(User user){
        if (user != null){
            sendUserToLandingPage(user);
        } else {
            Toast.makeText(this, "Username does not exist", Toast.LENGTH_SHORT).show();
        }
    }
    public void saveLoginSession(Context context, User user) {
        SharedPreferences prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", user.getUsername());
        // We can encrypt the password or better yet store an authentication token instead of raw details
        editor.putString("password", user.getPassword());
        editor.apply();
    }
}