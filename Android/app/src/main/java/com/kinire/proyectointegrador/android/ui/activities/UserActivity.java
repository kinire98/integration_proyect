package com.kinire.proyectointegrador.android.ui.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.controllers.activities.UserActivityController;
import com.kinire.proyectointegrador.android.utils.StyleDissonancesCorrection;
import com.kinire.proyectointegrador.android.utils.ThemeManager;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import io.shubh.superiortoastlibrary.SuperiorToastWithHeadersPreDesigned;

/**
 * Activity que se encarga del login de los usuarios
 */
public class UserActivity extends AppCompatActivity {

    private EditText userField;
    private Button button;

    private UserActivityController controller;


    private String invalidUserName;
    private String incorrectAdminPassword;

    private String connectivityError;

    private boolean showBackButton;

    private String PASSWORD_FIELD_SET_TITLE;
    private String PASSWORD_FIELD_QUERY_TITLE;

    private String PASSWORD_FIELD_SET_BUTTON;
    private String PASSWORD_FIELD_QUERY_BUTTON;

    private String INCORRECT_PASSWORD;

    private String EMPTY_PASSWORD;
    private String EMPTY_USER;

    private String SHOW_BACK_BUTTON_PARCELABLE_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.initializeElements();
        this.getBundleElements();
        this.setToolbar();
        this.setListeners();
        StyleDissonancesCorrection.setStatusBarCorrectColor(this);
    }

    private void getBundleElements() {
        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
            return;
        showBackButton = bundle.getBoolean(SHOW_BACK_BUTTON_PARCELABLE_KEY);
    }

    private void initializeElements() {
        this.userField = findViewById(R.id.user_write);
        this.button = findViewById(R.id.accept_username);
        this.incorrectAdminPassword = getString(R.string.incorrect_admin_password);
        this.invalidUserName = getString(R.string.invalid_user_name);
        this.connectivityError = getString(R.string.connectivity_error);
        this.PASSWORD_FIELD_SET_TITLE = getString(R.string.create_password);
        this.PASSWORD_FIELD_QUERY_TITLE = getString(R.string.write_password);
        this.PASSWORD_FIELD_SET_BUTTON = getString(R.string.create_password_button);
        this.PASSWORD_FIELD_QUERY_BUTTON = getString(R.string.write_password_button);
        this.INCORRECT_PASSWORD = getString(R.string.incorrect_password);
        this.EMPTY_PASSWORD = getString(R.string.empty_password);
        this.EMPTY_USER = getString(R.string.empty_username);
        this.SHOW_BACK_BUTTON_PARCELABLE_KEY = getString(R.string.show_back_parcelable_key);
        this.controller = new UserActivityController(this);
    }

    private void setToolbar() {
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
    }

    private void setListeners() {
        this.button.setOnClickListener(controller);
        this.userField.addTextChangedListener(controller);
    }

    public String getUserNameContent() {
        return userField.getText().toString();
    }

    /**
     * Devuelve el EditText para hacer posible esconder el teclado cuando se introduce el usuario
     * @return EdixText donde se escribe el nombre de usuario
     */
    public EditText getUserField() {
        return userField;
    }

    /**
     * Muestra un mensaje con un error de conectividad
     */
    public void connectivityError() {
        Toast.makeText(this, connectivityError, Toast.LENGTH_SHORT).show();
    }

    /**
     * Muestra un mensaje de error indicando al usuario que no puede dejar el campo del usuario
     * vacío
     */
    public void usernameEmpty() {
        runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(getApplicationContext()
                            ,SuperiorToastWithHeadersPreDesigned.ERROR_TOAST)
                    .setToastHeaderText(EMPTY_USER)
                    .show();
        });
    }
    /**
     * Muestra un mensaje de error indicando al usuario que no puede dejar el campo de la contraseña
     * vacío
     */
    public void passwordEmpty() {
        runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(getApplicationContext()
                            ,SuperiorToastWithHeadersPreDesigned.ERROR_TOAST)
                    .setToastHeaderText(EMPTY_PASSWORD)
                    .show();
        });
    }
    /**
     * Establece el contenido del campo del nombre de usuario
     * @param content El contenido a establecer
     */
    public void setUsernameContent(String content) {
        this.userField.setText(content);
    }



    /**
     * Método para preguntar por la contraseña al usuario.
     * Al llamarse siempre desde otro hilo que no es el principal, el dialog se tiene que ejecutar
     * en el hilo de la UI, y el otro hilo debería esperar hasta que el usuario complete la operación.
     * Se ejecuta cuando el usuario ya existe
     * @return Contraseña introducida por el usuario
     */
    public String askForPassword() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.ask_password_layout, null);
        EditText editText = dialogView.findViewById(R.id.password_field);
        TextView title = dialogView.findViewById(R.id.title);
        title.setText(PASSWORD_FIELD_QUERY_TITLE);
        Button button = dialogView.findViewById(R.id.login);
        button.setText(PASSWORD_FIELD_QUERY_BUTTON);
        final CountDownLatch latch = new CountDownLatch(1);
        final String[] key = {""};
        AtomicReference<Dialog> dialog = new AtomicReference<>();
        button.setOnClickListener(v -> {
            key[0] = editText.getText().toString();
            latch.countDown();
            dialog.get().dismiss();
        });
        runOnUiThread(() -> {
            dialog.set(new AlertDialog.Builder(this)
                    .setView(dialogView)
                    .create());
            dialog.get().show();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {}
        return key[0];
    }

    /**
     * Igual que el método anterior, solo que este se ejecuta si se necesita una contraseña nueva
     * @return Contraseña introducida por el usuario
     */
    public String askForNewPassword() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.ask_password_layout, null);
        EditText editText = dialogView.findViewById(R.id.password_field);
        TextView title = dialogView.findViewById(R.id.title);
        title.setText(PASSWORD_FIELD_SET_TITLE);
        Button button = dialogView.findViewById(R.id.login);
        button.setText(PASSWORD_FIELD_SET_BUTTON);
        final CountDownLatch latch = new CountDownLatch(1);
        final String[] key = {""};
        AtomicReference<Dialog> dialog = new AtomicReference<>();
        button.setOnClickListener(v -> {
            key[0] = editText.getText().toString();
            latch.countDown();
            dialog.get().dismiss();
        });
        runOnUiThread(() -> {
            dialog.set(new AlertDialog.Builder(this)
                    .setView(dialogView)
                    .create());
            dialog.get().show();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {}
        return key[0];
    }

    /**
     * Toast de error para indicarle al usuario que introdujo la contraseña incorrecta.
     */
    public void incorrectPassword() {
        runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(this,
                            SuperiorToastWithHeadersPreDesigned.ERROR_TOAST)
                    .setToastHeaderText(INCORRECT_PASSWORD)
                    .show();
        });
    }

}