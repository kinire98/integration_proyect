package com.kinire.proyectointegrador.android.controllers.activities;

import static androidx.core.content.ContextCompat.getSystemService;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.kinire.proyectointegrador.android.controllers.fragments.ShoppingCartFragmentController;
import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.activities.UserActivity;
import com.kinire.proyectointegrador.android.utils.UserAdmin;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.User;

/**
 * Controlador del Activity User. Se encarga de la gestión de los usuarios y las llamadas al servidor
 */
public class UserActivityController implements View.OnClickListener, TextWatcher {

    private UserActivity activity;

    private User user;

    private String username;

    private UserAdmin userAdmin;

    private boolean textChangedByController = false;


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
        startUserOperations();
    }
    private void startUserOperations() {
        if(this.username.isEmpty()) {
            activity.usernameEmpty();
            return;
        }
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
        String password = activity.askForPassword();
        this.user = new User(username, password);
        if(password.isEmpty()) {
            activity.passwordEmpty();
            return;
        }
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if(textChangedByController) {
            textChangedByController = false;
            return;
        }
        String userContent = s.toString();
        if(userContent.isEmpty())
            return;
        if(userContent.charAt(userContent.length() - 1) != '\n')
            return;
        this.username = userContent.substring(0, userContent.length() - 1);
        this.activity.setUsernameContent(username);
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getUserField().getWindowToken(), 0);
        startUserOperations();
    }
}
