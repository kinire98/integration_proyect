package com.kinire.proyectointegrador.android.ui.fragments.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShoppingCartViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ShoppingCartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}