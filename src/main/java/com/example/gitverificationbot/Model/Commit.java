package com.example.gitverificationbot.Model;

import com.google.gson.annotations.SerializedName;

public class Commit {
    @SerializedName("message")
    public String message;

    public Commit(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
