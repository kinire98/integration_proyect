package com.kinire.proyectointegrador.users;

import com.kinire.proyectointegrador.components.User;

import java.io.Serial;
import java.io.Serializable;

public class UserMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 9L;

    private final boolean insertUserRequest;

    private final boolean selectUserRequest;

    private final boolean isUserDataCorrectRequest;

    private final String username;

    private final User user;

    UserMessage(boolean insertUserRequest, boolean selectUserRequest,
                boolean isUserDataCorrectRequest, String username,
                User user) {
        this.insertUserRequest = insertUserRequest;
        this.selectUserRequest = selectUserRequest;
        this.isUserDataCorrectRequest = isUserDataCorrectRequest;
        this.username = username;
        this.user = user;
    }

    public boolean isInsertUserRequest() {
        return insertUserRequest;
    }

    public boolean isSelectUserRequest() {
        return selectUserRequest;
    }

    public boolean isUserDataCorrectRequest() {
        return isUserDataCorrectRequest;
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }
}
