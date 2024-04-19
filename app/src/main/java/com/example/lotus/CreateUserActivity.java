package com.example.lotus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.User;
import com.example.lotus.databinding.ActivityCreateUserBinding;

import java.util.Objects;

public class CreateUserActivity extends AppCompatActivity {
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
        ActivityCreateUserBinding binding = ActivityCreateUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button button = binding.registerButton;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = binding.editTextTextEmailAddress2.getText().toString();
                password = binding.editTextTextPassword2.getText().toString();

                if(VerifyLogin.checkUserExists(repository,username)) {
                    Toast.makeText(getApplicationContext(), "Account Already exist",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Successful Creation of Username: " + username,Toast.LENGTH_SHORT).show();
                    Intent i = intentFactory.createIntent(getApplicationContext(), LoginActivity.class);
                    i.putExtra(Constants.USERNAME_REGISTERD, username);
                    startActivity(i);
                }
        }
        });
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
            Intent intent = intentFactory.createIntent(getApplicationContext(), LandingPage.class);
            intent.putExtra(Constants.LOGIN_ACTIVITY_KEY, username);
            startActivity(intent);
            return true;
        } else {
            Toast.makeText(this, "Username does not exist", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}