package com.kinire.proyectointegrador.android.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.controllers.activities.MainActivityController;
import com.kinire.proyectointegrador.android.databinding.ActivityMainBinding;
import com.kinire.proyectointegrador.android.utils.ThemeManager;
import com.kinire.proyectointegrador.client.Connection;

/**
 * Activity principal de la aplicación.
 * El código referente a la creación de la estructura para los fragments está generado automáticamente por
 * Android Studio.
 * Se quita el título de la ActionBar, se crean métodos para esconder ciertas partes de la aplicación
 * dependiendo del usuario que las utilice y se crean un menú de opciones y se controlan los eventos
 * de pulsado de los iconos
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MainActivityController controller;

    private BottomNavigationView bottomMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initalizeElements();
        if(controller.isStartUserActivity())
            return;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.bottomMenu = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_products_list, R.id.navigation_shopping_cart, R.id.navigation_history_purchase, R.id.navigation_add_product)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        this.setUpToolbar();
        controller.checkForUserFunctions();
    }
    private void setUpToolbar() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
    }

    private void initalizeElements() {
        this.controller = new MainActivityController(this);
    }

    public void hideAddProducts() {
        bottomMenu.getMenu().findItem(R.id.navigation_add_product).setVisible(false);
    }

    public void showAddProducts() {
        bottomMenu.getMenu().findItem(R.id.navigation_add_product).setVisible(true);
    }

    public void hideCart() {
        bottomMenu.getMenu().findItem(R.id.navigation_shopping_cart).setVisible(false);
    }

    public void showCart() {
        bottomMenu.getMenu().findItem(R.id.navigation_shopping_cart).setVisible(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.login) {
            controller.startUserNameActivity();
            return true;
        }
        if(item.getItemId() == R.id.settings) {
            controller.startSettingsActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
