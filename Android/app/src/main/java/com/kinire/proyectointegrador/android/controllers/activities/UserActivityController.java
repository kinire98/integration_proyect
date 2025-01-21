package com.kinire.proyectointegrador.android.controllers.activities;

import android.content.Intent;
import android.view.View;

import com.kinire.proyectointegrador.android.controllers.fragments.HistoryPurchaseFragmentController;
import com.kinire.proyectointegrador.android.controllers.fragments.ShoppingCartFragmentController;
import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.activities.UserActivity;
import com.kinire.proyectointegrador.android.user_admin.UserAdmin;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.User;

public class UserActivityController implements View.OnClickListener {

    private UserActivity activity;

    private User user;

    private String username;

    private UserAdmin userAdmin;

    public UserActivityController(UserActivity activity) {
        this.activity = activity;
        this.userAdmin = new UserAdmin(activity);
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
        this.user = new User(username, activity.askForPassword());
        Connection.getInstance().isUserDataCorrect(user, () -> {
           userAdmin.setUser(user);
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
            userAdmin.setUser(user);
            returnToMainActivity();
        }, (e) -> {
            activity.connectivityError();
        });

    }
    private void returnToMainActivity() {
        ShoppingCartFragmentController.emptyCart();
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
}
