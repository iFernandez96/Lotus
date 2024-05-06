package com.example.lotus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;

public class ProfileActivity extends AppCompatActivity {
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    LoginRepo repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile);
        Button backButton = findViewById(R.id.back_button);
        Button deleteUserButton = findViewById(R.id.deleteUser);

        repository = LoginRepo.getRepo(getApplication());

        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitLoginSession(getApplicationContext());
                startActivity(intentFactory.createIntent(getApplicationContext(), LandingPage.class));
            }
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
                startActivity(intentFactory.createIntent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
    private void updateProfile() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        repository.updateUser(name, email, password);
        showToast("Updating profile with name: " + name + ", email: " + email);

        startActivity(intentFactory.createIntent(getApplicationContext(), LoginActivity.class));
    }

    public void exitLoginSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();
    }
    private void deleteUser() {
        // Handle delete user logic here
        showToast("User Deleted");
        repository.deleteUser();
        exitLoginSession(getApplicationContext());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}