package com.kinire.proyectointegrador.android.controllers.activities;

import android.content.Intent;

import com.kinire.proyectointegrador.android.shared_preferences.SharedPreferencesManager;
import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.activities.SettingsActivity;
import com.kinire.proyectointegrador.android.ui.activities.UserActivity;
import com.kinire.proyectointegrador.android.users.UserAdmin;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainActivityController {

    private MainActivity activity;

    private SharedPreferencesManager sharedPreferencesManager;


    private UserAdmin username;

    private List<Product> productList;


    public MainActivityController(MainActivity activity) {
        this.activity = activity;
        this.sharedPreferencesManager = new SharedPreferencesManager(activity);
        this.username = new UserAdmin(this.sharedPreferencesManager.getUser());
        userNameManagement();
    }
    private void userNameManagement() {
        if(!username.isValidUser()) {
            startUserNameActivity();
            return;
        }
        if(username.isAdmin()) {
            this.activity.showAddProducts();
        } else {
            this.activity.hideAddProducts();
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
