package com.kinire.proyectointegrador.android.ui.fragments.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryPurchaseViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HistoryPurchaseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
