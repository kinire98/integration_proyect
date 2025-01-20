package com.kinire.proyectointegrador.android.ui.fragments.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kinire.proyectointegrador.components.Purchase;

import java.util.List;

public class HistoryPurchaseViewModel extends ViewModel {

    private final MutableLiveData<List<Purchase>> mPurchases;

    private final MutableLiveData<String> mNoPurchases;

    public HistoryPurchaseViewModel() {
        mPurchases = new MutableLiveData<>();
        mNoPurchases = new MutableLiveData<>();
    }

    public void setPurchases(List<Purchase> purchases) {
        mPurchases.setValue(purchases);
    }
    public LiveData<List<Purchase>> getPurchases() {
        return mPurchases;
    }

    public void setNoPurchasesMessage(String noPurchases) {
        mNoPurchases.setValue(noPurchases);
    }

    public MutableLiveData<String> getNoPurchasesMessage() {
        return mNoPurchases;
    }
}
