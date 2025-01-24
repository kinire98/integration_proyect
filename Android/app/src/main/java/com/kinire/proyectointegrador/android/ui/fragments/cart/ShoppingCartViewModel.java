package com.kinire.proyectointegrador.android.ui.fragments.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Clase Modelo-Vista encargada de la persistencia de los datos, a la hora de cambiar los fragmentos
 */
import com.kinire.proyectointegrador.components.Purchase;

public class ShoppingCartViewModel extends ViewModel {

    private final MutableLiveData<Purchase> mPurchase;

    private final MutableLiveData<String> mEmptyMessage;

    public ShoppingCartViewModel() {
        mPurchase = new MutableLiveData<>();
        mEmptyMessage = new MutableLiveData<>();
    }

    public MutableLiveData<Purchase> getData() {
        return mPurchase;
    }
    public void setData(Purchase purchase) {
        mPurchase.setValue(purchase);
    }

    public MutableLiveData<String> getmEmptyMessage() {
        return mEmptyMessage;
    }
    public void setEmptyMessage(String message) {
        mEmptyMessage.setValue(message);
    }

    public void forceRefresh() {
        mPurchase.setValue(mPurchase.getValue());
    }
}