package com.kinire.proyectointegrador.android.controllers.activities;

import android.content.Intent;

import com.kinire.proyectointegrador.android.shared_preferences.SharedPreferencesManager;
import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.activities.SettingsActivity;
import com.kinire.proyectointegrador.android.ui.activities.UserActivity;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainActivityController {

    private final User username;
    private MainActivity activity;

    private SharedPreferencesManager sharedPreferencesManager;



    private List<Product> productList;


    public MainActivityController(MainActivity activity) {
        this.activity = activity;
        this.sharedPreferencesManager = new SharedPreferencesManager(activity);
        this.username = new User();
        this.username.setUser(this.sharedPreferencesManager.getUser());
        userNameManagement();
    }
    private void userNameManagement() {
        if(username.getUser().isEmpty()) {
            startUserNameActivity();
            return;
        }
        if(username.isAdmin()) {
            this.activity.showAddProducts();
            this.activity.hideHistory();
            this.activity.hideCart();
        } else {
            this.activity.hideAddProducts();
            this.activity.showHistory();
            this.activity.showCart();
        }
    }
    public void startUserNameActivity() {
        Intent intent = new Intent(activity, UserActivity.class);
        activity.startActivity(intent);
    }

    public void startSettingsActivity() {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }


}
