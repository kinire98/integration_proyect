package com.kinire.proyectointegrador.android.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.kinire.proyectointegrador.client.lamba_interfaces.EmptyFunction;
import com.kinire.proyectointegrador.components.User;

public class DeletedPurchase {
    private static IdFunction updateCallback;
    private static long id;
    public static void deletedPurchase(long id) {
        DeletedPurchase.id = id;
        updateCallback.apply(id);
    }

    public static void setUpdateCallback(IdFunction updateCallback) {
        DeletedPurchase.updateCallback = updateCallback;
    }
}
