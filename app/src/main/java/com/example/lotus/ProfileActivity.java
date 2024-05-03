package com.example.lotus;

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
        Button logoutButton = findViewById(R.id.Logout_button);
        Button deleteUserButton = findViewById(R.id.deleteUser);

        repository = LoginRepo.getRepo(getApplication());

        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
    }
    private void updateProfile() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        repository.updateUser(name, email, password);
        showToast("Updating profile with name: " + name + ", email: " + email);
    }

    private void logout() {
        // Handle logout logic here, e.g., clearing user data or SharedPreferences
        showToast("User Logged Out");
        finish(); // Close the activity
    }

    private void deleteUser() {
        // Handle delete user logic here
        showToast("User Deleted");
        finish(); // Close the activity
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}