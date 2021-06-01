package com.example.gitverificationbot.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    public User(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @SerializedName("login")
    public String login;
}
