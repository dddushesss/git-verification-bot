package com.example.gitverificationbot.Model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

enum PullRequestState{

}

@Data
public class PullRequest {
    @SerializedName("user")
    private User user;
    @SerializedName("body")
    private String body;
    @SerializedName("title")
    private String title;
    @SerializedName("state")
    private String state;
    @SerializedName("number")
    private Integer number;

}
