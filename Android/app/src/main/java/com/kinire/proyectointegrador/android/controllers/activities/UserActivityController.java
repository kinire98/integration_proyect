package com.kinire.proyectointegrador.android.controllers.activities;

import android.content.Intent;
import android.view.View;

import com.kinire.proyectointegrador.android.shared_preferences.SharedPreferencesManager;
import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.activities.UserActivity;
import com.kinire.proyectointegrador.android.users.UserAdmin;
import com.kinire.proyectointegrador.client.Connection;

public class UserActivityController implements View.OnClickListener {

    private UserActivity activity;

    private UserAdmin user;

    private SharedPreferencesManager sharedPreferencesManager;

    public UserActivityController(UserActivity activity) {
        this.activity = activity;
        this.sharedPreferencesManager = new SharedPreferencesManager(activity);
    }

    @Override
    public void onClick(View v) {
        this.user = new UserAdmin(activity.getUserNameContent());
        if(!user.isValidUser()) {
            activity.invalidUserName();
            return;
        }
        if(user.isAdmin()) {
            String password = activity.askForPassword();
            if(Connection.getInstance().isAdminPasswordCorrect(password)) {
                sharedPreferencesManager.setUser(user.getUser());
                returnToMainActivity();
            } else {
                activity.incorrectAdminPassword();
            }
            return;
        }
        sharedPreferencesManager.setUser(user.getUser());
        returnToMainActivity();
    }
    private void returnToMainActivity() {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
}
