package com.kinire.proyectointegrador.android.controllers.activities;

import android.content.Intent;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.activities.SettingsActivity;
import com.kinire.proyectointegrador.android.ui.activities.UserActivity;
import com.kinire.proyectointegrador.android.utils.UserAdmin;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.User;

import java.util.List;

/**
 * Controlador para el MainActivity.
 * Este se encarga de decidir si es necesario iniciar la vista para que el usuario inicie sesión y
 * que elementos va a enseñar en la barra inferior de navegación
 */
public class MainActivityController {

    private final User username;
    private final MainActivity activity;

    private final UserAdmin userAdmin;

    private List<Product> productList;

    private boolean startUserActivity = false;

    private String PARCELABLE_SHOW_BACK_BUTTON_KEY;

    public MainActivityController(MainActivity activity) {
        this.activity = activity;
        this.userAdmin = new UserAdmin(activity);
        this.username = userAdmin.getUser();
        this.PARCELABLE_SHOW_BACK_BUTTON_KEY = activity.getString(R.string.show_back_parcelable_key);
        userNameManagement();
    }
    private void userNameManagement() {
        if(username.getUser().isEmpty()) { // Si no hay ningún usuario guardado, se va a la parte de inicio de sesión
            startUserNameActivity();
            return;
        }

    }

    /**
     * Método encargado de iniciar el Activity para que el usuario introduzca sus datos de contacto.
     * En caso de no haber ningún usuario guardado, se eliminará del stack de Activities al MainActivity
     * para evitar que se pueda interactuar con la aplicación sin haber iniciado sesión
     */
    public void startUserNameActivity() {
        Intent intent = new Intent(activity, UserActivity.class);
        if(username.getUser().isEmpty()) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startUserActivity = true;
        }
        intent.putExtra(PARCELABLE_SHOW_BACK_BUTTON_KEY, !startUserActivity);
        activity.startActivity(intent);
    }

    public boolean isStartUserActivity() {
        return startUserActivity;
    }

    public void checkForUserFunctions() {
        if(username.isAdmin()) {
            this.activity.showAddProducts();
            this.activity.hideCart();
        } else {
            this.activity.hideAddProducts();
            this.activity.showCart();
        }
    }

    /**
     * Inicia el Activity para cambiar algunos ajustes de la aplicación
     */
    public void startSettingsActivity() {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }


}
