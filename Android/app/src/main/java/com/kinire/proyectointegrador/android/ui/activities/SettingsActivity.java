package com.kinire.proyectointegrador.android.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.controllers.activities.SettingsActivityController;
import com.kinire.proyectointegrador.android.utils.StyleDissonancesCorrection;
import com.kinire.proyectointegrador.android.utils.ThemeManager;

import java.io.PipedOutputStream;

/**
 * Activity que muestra los ajustes de la aplicación.
 * Estos son limitados, ya que solo se muestra uno y su funcionalidad no ha podido ser implementada
 */
public class SettingsActivity extends AppCompatActivity {

    private Spinner spinner;

    private SettingsActivityController controller;

    private static final String DIALOG_TITLE = "Característica no implementada";
    private static final String DIALOG_POSITIVE_BUTTON_TEXT = "OK";
    private static final String DIALOG_MESSAGE = "La preferencia se guarda en la SharedPreferences. Sin embargo, por falta de tiempo, no fue " +
            "posible implementar la funcionalidad de cambiar el tema correctamente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        StyleDissonancesCorrection.setStatusBarCorrectColor(this);
        this.initElements();
        this.setListeners();
        this.setAdapter();
    }
    private void initElements() {
        this.spinner = findViewById(R.id.theme_spinner);
        this.controller = new SettingsActivityController(this);
    }
    private void setListeners() {
        this.spinner.setOnItemSelectedListener(controller);
    }

    private void setAdapter() {
        this.spinner.setAdapter(controller.getAdapter());
    }

    /**
     * Aviso a la hora de cambiar el tema de que no se va a encontrar ningún cambio ya que la característica
     * no está implementada.
     */
    public void unsuportedFeature() {
        new AlertDialog.Builder(this)
                .setTitle(DIALOG_TITLE)
                .setPositiveButton(DIALOG_POSITIVE_BUTTON_TEXT, (dialog1, which) -> dialog1.dismiss())
                .setMessage(DIALOG_MESSAGE).show();
    }

}
