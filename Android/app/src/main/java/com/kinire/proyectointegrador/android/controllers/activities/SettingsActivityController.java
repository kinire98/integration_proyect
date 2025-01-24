package com.kinire.proyectointegrador.android.controllers.activities;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.ui.activities.SettingsActivity;
import com.kinire.proyectointegrador.android.utils.ThemeManager;

/**
 * Clase encargada de controlar el Activity Settings
 * Hace de Listener para un Spinner.
 * Su funcionalidad es inicializar los elementos del Spinner y cuándo se selecciona uno de ellos
 * guardar la opción en las SharedPreferences. Al no poder terminar de implementar la característica
 * sale un AlertDialog indicando que la característica no está implementada
 */
public class SettingsActivityController implements AdapterView.OnItemSelectedListener {

    private final SettingsActivity activity;

    private final ArrayAdapter<CharSequence> adapter;

    private final ThemeManager manager;

    private boolean firstTime = true;

    public SettingsActivityController(SettingsActivity activity) {
        this.activity = activity;
        this.adapter = ArrayAdapter.createFromResource(
                activity,
                R.array.themes,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.manager = new ThemeManager(activity);
    }

    public ArrayAdapter<CharSequence> getAdapter() {
        return adapter;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(!firstTime)
            activity.unsuportedFeature();
        firstTime = false;
        if(position == 0)
            this.manager.setTheme(ThemeManager.Options.DEFAULT_THEME);
        else if(position == 1)
            this.manager.setTheme(ThemeManager.Options.LIGHT_THEME);
        else if (position == 2)
            this.manager.setTheme(ThemeManager.Options.DARK_THEME);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
