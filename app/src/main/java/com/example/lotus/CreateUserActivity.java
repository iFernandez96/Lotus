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

                if(repository.getUserByUsername(username)!=null) {
                    Toast.makeText(getApplicationContext(), "Account Already exist",Toast.LENGTH_SHORT).show();
                }else{
                    repository.insertUser2Database(new User(username, password));
                    Toast.makeText(getApplicationContext(), "Successful Creation of Username: " + username,Toast.LENGTH_SHORT).show();
                    Intent i = intentFactory.createIntent(getApplicationContext(), LoginActivity.class);
                    i.putExtra(Constants.USERNAME_REGISTERD, username);
                    startActivity(i);
                }
        }
        });
    }

    private void insertUserRecord(String Username, String Password) {
        User user = repository.getUserByUsername(Username);
        if (user!=null) {
            Toast.makeText(this, "The User already exists. Please enter a new Username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Password.isEmpty()) {
            Toast.makeText(this, "Please enter a valid Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Username.isEmpty()) {
            Toast.makeText(this, "Please enter a valid Username", Toast.LENGTH_SHORT).show();
            return;
        }
        repository.insertUser2Database(new User(Username, Password));
        Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();
    }
}