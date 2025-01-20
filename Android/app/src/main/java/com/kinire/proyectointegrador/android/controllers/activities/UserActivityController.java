package com.kinire.proyectointegrador.android.controllers.activities;

import android.content.Intent;
import android.view.View;

import com.kinire.proyectointegrador.android.shared_preferences.SharedPreferencesManager;
import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.activities.UserActivity;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.User;

public class UserActivityController implements View.OnClickListener {

    private UserActivity activity;

    private User user;

    private String username;

    private SharedPreferencesManager sharedPreferencesManager;

    public UserActivityController(UserActivity activity) {
        this.activity = activity;
        this.sharedPreferencesManager = new SharedPreferencesManager(activity);
    }

    @Override
    public void onClick(View v) {
        this.username = activity.getUserNameContent();
        if(!Connection.isInstanceStarted()) {
            Connection.startInstance(() -> {
                Connection.getInstance().userExists(this.username, this::userExists, this::userDoesntExist, (e) -> {
                    activity.connectivityError();
                });
            });
        } else {
            Connection.getInstance().userExists(this.username, this::userExists, this::userDoesntExist, (e) -> {
                activity.connectivityError();
            });
        }
    }

    private void userExists() {
        System.out.println("Aqui");
        this.user = new User(username, activity.askForPassword());
        Connection.getInstance().isUserDataCorrect(user, () -> {
           sharedPreferencesManager.setUser(user);
           returnToMainActivity();
        },  () -> {
            activity.incorrectPassword();
        }, (e) -> {
            activity.connectivityError();
        });
    }

    private void userDoesntExist() {
        System.out.println("Aqui no existe");
        this.user = new User(username, activity.askForNewPassword());
        Connection.getInstance().insertUserData(user, () -> {
            sharedPreferencesManager.setUser(user);
            returnToMainActivity();
        }, (e) -> {
            activity.connectivityError();
        });

    }
    private void returnToMainActivity() {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
}
