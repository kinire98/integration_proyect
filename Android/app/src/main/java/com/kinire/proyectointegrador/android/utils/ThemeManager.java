package com.kinire.proyectointegrador.android.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.annotation.StyleRes;

import com.kinire.proyectointegrador.android.R;

/**
 * Administrador de temas. el tema seleccionado se guarda en las SharedPreferences
 */
public class ThemeManager {
    private final SharedPreferences sharedPreferences;

    private final String THEME_SHARED_PREFERENCES_KEY;
    private final Context context;
    public enum Options {
        DEFAULT_THEME(0),
        LIGHT_THEME(1),
        DARK_THEME(2);
        private int value;
        Options(int i) {
            this.value = i;
        }

    }

    public ThemeManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
        this.THEME_SHARED_PREFERENCES_KEY = context.getString(R.string.theme_shared_preferences_key);
    }

    /**
     * Devuelve el identificador del estilo que se ha guardado en las SharedPreferences.
     * Si es el por defecto, recoge el estilo del móvil en general
     * @return El identificador del estilo
     */
    public @StyleRes int getTheme() {
        int value = sharedPreferences.getInt(THEME_SHARED_PREFERENCES_KEY, -1);
        if(value == Options.LIGHT_THEME.value)
            return R.style.Theme_Android;
        else if (value == Options.DARK_THEME.value)
            return R.style.Theme_Android;
        return getDefaultTheme();
    }
    private @StyleRes int getDefaultTheme() {
        int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(currentNightMode == Configuration.UI_MODE_NIGHT_NO)
            return R.style.Theme_Android;
        else
            return R.style.Theme_Android;
    }

    /**
     * Establece el tema y lo guarda a las SharedPreferences
     * @param option Variante del enum del valor de la interfaz a guardar
     */
    public void setTheme(Options option) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(THEME_SHARED_PREFERENCES_KEY, option.value);
        editor.apply();
    }
}
