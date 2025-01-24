package com.kinire.proyectointegrador.android.controllers.activities;

import android.content.Intent;
import android.view.View;

import com.kinire.proyectointegrador.android.controllers.fragments.ShoppingCartFragmentController;
import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.activities.UserActivity;
import com.kinire.proyectointegrador.android.utils.UserAdmin;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.User;

/**
 * Controlador del Activity User. Se encarga de la gestión de los usuarios y las llamadas al servidor
 */
public class UserActivityController implements View.OnClickListener {

    private UserActivity activity;

    private User user;

    private String username;

    private UserAdmin userAdmin;

    public UserActivityController(UserActivity activity) {
        this.activity = activity;
        this.userAdmin = new UserAdmin(activity);
    }

    /**
     * Este método se encarga de recoger el nombre de usuario que introdujo el usuario.
     * Si no está inicializada la conexión, la inicia y sino la utiliza directamente.
     * En caso de existir el nombre de usuario, se pedirá que introduzca la contraseña de dicho usuario
     * En caso de no existir, se pedirá al usuario que introduzca una nueva contraseña.
     * @param v The view that was clicked.
     */
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
