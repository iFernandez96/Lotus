package com.example.lotus;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.User;

public class UserViewModel extends ViewModel {
    private final LoginRepo repository;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public UserViewModel(Application app) {
        this.repository = LoginRepo.getRepo(app);
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void fetchUserByUsername(String username) {
        new Thread(() -> {
            User user = repository.getUserByUsername(username);
            userLiveData.postValue(user); // Post the result back to the LiveData object on the main thread
        }).start();
    }

    public void updateUser(User user) {
        new Thread(() -> repository.updateUser(user.getName(), user.getEmail(), user.getPassword())).start();
    }

    public void deleteUser() {
        new Thread(repository::deleteUser).start();
    }
}
