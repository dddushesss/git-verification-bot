package com.example.gitverificationbot.Model;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Issue {
    @SerializedName("user")
    public User user;
    @SerializedName("body")
    public String body;
    @SerializedName("id")
    private int id;

    public Issue(String body){
        this.body = body;
    }
}
