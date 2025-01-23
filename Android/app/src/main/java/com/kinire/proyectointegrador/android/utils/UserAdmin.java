package com.kinire.proyectointegrador.android.utils;

import android.content.Context;

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
