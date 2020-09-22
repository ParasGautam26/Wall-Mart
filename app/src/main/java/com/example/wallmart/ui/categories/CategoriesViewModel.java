package com.example.wallmart.ui.categories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoriesViewModel extends ViewModel {

        public String name,desc,thumb;

    public CategoriesViewModel(String name, String desc, String thumb) {
        this.name = name;
        this.desc = desc;
        this.thumb = thumb;
    }
}