package com.example.travel_plan.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travel_plan.entities.User;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<User> user = new MutableLiveData<>();

    public LiveData<User> getUser(){
        return this.user;
    }
    public void setUser(User _user){
        System.out.println("set user");
        this.user.setValue(_user);
    }
}
