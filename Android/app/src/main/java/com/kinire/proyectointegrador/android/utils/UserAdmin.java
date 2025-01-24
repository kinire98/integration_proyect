package com.kinire.proyectointegrador.android.utils;

import android.content.Context;

import com.kinire.proyectointegrador.android.sqlite.UserDAO;
import com.kinire.proyectointegrador.components.User;

/**
 * Clase de administración de los usuarios.
 * No es de mucha utilidad pero se guardó, al ser utilizada en muchos sitios y sirve de intermediario con
 * el DAO
 */
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
