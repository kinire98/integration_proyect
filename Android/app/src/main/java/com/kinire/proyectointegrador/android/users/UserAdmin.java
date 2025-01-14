package com.kinire.proyectointegrador.android.users;

public class UserAdmin {

    private String user;
    private final static String ADMIN_USERNAME = "admin";

    public UserAdmin(String user) {
        this.user = user;
    }

    public boolean isAdmin() {
        return this.user.equals(ADMIN_USERNAME);
    }
    public boolean isValidUser() {
        return !user.isEmpty();
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UserAdmin{" +
                "user='" + user + '\'' +
                '}';
    }
}
