package com.example.lotus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.User;

public class UserViewModel extends ViewModel {
    private final LoginRepo repository;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public UserViewModel(LoginRepo repository) {
        this.repository = repository;
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void fetchUserByUsername(String username) {
        // Assuming repository has a method to fetch user by username
        User user = repository.getUserByUsername(username);
        userLiveData.postValue(user);
    }

    public void updateUser(User user) {
        // Assuming repository has a method to update user
        repository.updateUser(user.getName(), user.getEmail(), user.getPassword());
        userLiveData.postValue(user);
    }

    public void deleteUser() {
        // Assuming repository has a method to delete user
        User user = userLiveData.getValue();
        if (user != null) {
            repository.deleteUser(user);
            userLiveData.postValue(null);
        }
    }
}
