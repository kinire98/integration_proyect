package com.kinire.proyectointegrador.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.kinire.proyectointegrador.android.R;

public class SharedPreferencesManager {
    private Context context;

    private SharedPreferences sharedPreferences;

    private final String USER_SHARED_PREFERENCES_KEY;

    public SharedPreferencesManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
        this.USER_SHARED_PREFERENCES_KEY = context.getString(R.string.user_shared_preferences_key);
    }

    public String getUser() {
        return sharedPreferences.getString(USER_SHARED_PREFERENCES_KEY, "");
    }

    public void setUser(String user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_SHARED_PREFERENCES_KEY, user);
        editor.apply();
    }

}
