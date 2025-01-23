package com.kinire.proyectointegrador.android.user_admin;

import android.content.Context;

import com.kinire.proyectointegrador.android.shared_preferences.SharedPreferencesManager;
import com.kinire.proyectointegrador.components.User;

public class UserAdmin {
    private final SharedPreferencesManager sharedPreferencesManager;
    public UserAdmin(Context context) {
        this.sharedPreferencesManager = new SharedPreferencesManager(context);
    }
    public User getUser() {
        User user = new User();
        user.setUser(sharedPreferencesManager.getUser());
        return user;
    }
    public void setUser(User user) {
        sharedPreferencesManager.setUser(user.getUser());
    }
}
