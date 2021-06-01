package com.example.gitverificationbot.Model;

import com.google.gson.annotations.SerializedName;

public class Repository {
    @SerializedName("url")
    public String url;
    @SerializedName("owner")
    public User user;
    @SerializedName("description")
    public String description;

    public static String getGitUrl(Repository repository) {
        return repository.url;
    }

    public void setGitURL(String x) {
        url = x;
    }
}
