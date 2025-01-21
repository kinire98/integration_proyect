package com.kinire.proyectointegrador.android.controllers.activities;

import android.content.Intent;

import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.activities.SettingsActivity;
import com.kinire.proyectointegrador.android.ui.activities.UserActivity;
import com.kinire.proyectointegrador.android.user_admin.UserAdmin;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainActivityController {

    private final User username;
    private final MainActivity activity;

    private final UserAdmin userAdmin;

    private List<Product> productList;


    public MainActivityController(MainActivity activity) {
        this.activity = activity;
        this.userAdmin = new UserAdmin(activity);
        this.username = userAdmin.getUser();
        userNameManagement();
    }
    private void userNameManagement() {
        if(username.getUser().isEmpty()) {
            startUserNameActivity();
            return;
        }
        if(username.isAdmin()) {
            this.activity.showAddProducts();
            this.activity.hideCart();
        } else {
            this.activity.hideAddProducts();
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
