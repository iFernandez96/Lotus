package com.example.lotus;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.Statistics;

public class StatisticsViewModel extends AndroidViewModel {
    private LoginRepo loginRepo;
    private MutableLiveData<Statistics> statisticsLiveData;

    public StatisticsViewModel(@NonNull Application application) {
        super(application);
        loginRepo = LoginRepo.getRepo(application);
        statisticsLiveData = new MutableLiveData<>();
    }

    public LiveData<Statistics> getStatisticsByUserId(int userId) {
        // This will be async call, so LiveData should be handled accordingly
        new Thread(() -> {
            Statistics statistics = loginRepo.getStatisticsByUserID(userId);
            statisticsLiveData.postValue(statistics);
        }).start();
        return statisticsLiveData;
    }

    public void insertStatistics(Statistics statistics) {
        loginRepo.insertStatistics(statistics);
    }
}
