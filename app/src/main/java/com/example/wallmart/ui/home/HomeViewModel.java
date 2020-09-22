package com.example.wallmart.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    public String name,desc,thumb;
    public HomeViewModel(String name, String desc, String thumb) {
        this.name = name;
        this.desc = desc;
        this.thumb = thumb;
    }
}