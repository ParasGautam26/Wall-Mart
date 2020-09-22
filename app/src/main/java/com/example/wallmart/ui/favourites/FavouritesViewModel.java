package com.example.wallmart.ui.favourites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavouritesViewModel extends ViewModel {
    public String thumb;
    public FavouritesViewModel() {

    }
    public FavouritesViewModel(String thumb) {
        this.thumb = thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}