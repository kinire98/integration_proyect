package com.kinire.proyectointegrador.components;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    private String user;
    private String password;

    private static final String ADMIN_USERNAME = "admin";

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }
    public User() {
        this.user = "";
        this.password = "";
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return user.equals(ADMIN_USERNAME);
    }

    public static String getAdminUsername() {
        return ADMIN_USERNAME;
    }
}
