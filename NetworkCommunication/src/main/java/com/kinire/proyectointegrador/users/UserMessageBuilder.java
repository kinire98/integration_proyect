package com.kinire.proyectointegrador.users;

import com.kinire.proyectointegrador.components.User;

public class UserMessageBuilder {

    private boolean insertUserRequest;

    private boolean selectUserRequest;

    private boolean isUserDataCorrectRequest;

    private String username;

    private User user;

    public UserMessageBuilder() {}

    public UserMessageBuilder insertUserRequest(User user) {
        if (
                this.selectUserRequest ||
                        this.isUserDataCorrectRequest
        )
            throw new IllegalStateException("Only one request");
        this.insertUserRequest = true;
        this.user = user;
        return this;
    }

    public UserMessageBuilder selectUserRequest(String username) {
        if (
                this.insertUserRequest ||
                        this.isUserDataCorrectRequest
        )
            throw new IllegalStateException("Only one request");
        this.selectUserRequest = true;
        this.username = username;
        return this;
    }

    public UserMessageBuilder isUserDataCorrect(User user) {
        if (
                this.insertUserRequest ||
                        this.selectUserRequest
        )
            throw new IllegalStateException("Only one request");
        this.isUserDataCorrectRequest = true;
        this.user = user;
        return this;
    }

    public UserMessage build() {
        return new UserMessage(
                insertUserRequest,
                selectUserRequest,
                isUserDataCorrectRequest,
                username,
                user
        );
    }


}
