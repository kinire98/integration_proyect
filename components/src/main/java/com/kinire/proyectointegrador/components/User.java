package com.kinire.proyectointegrador.components;

import java.io.Serializable;

public class User implements Serializable {

    private String user;
    private String password;

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
}
