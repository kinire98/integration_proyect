package com.kinire.proyectointegrador.android.utils;

import android.content.Context;

import com.kinire.proyectointegrador.android.sqlite.UserDAO;
import com.kinire.proyectointegrador.components.User;

public class UserAdmin {
    private final UserDAO userDAO;
    public UserAdmin(Context context) {
        this.userDAO = new UserDAO(context);
    }
    public User getUser() {
        return userDAO.getUser();
    }
    public void setUser(User user) {
        userDAO.setUser(user);
    }
}
