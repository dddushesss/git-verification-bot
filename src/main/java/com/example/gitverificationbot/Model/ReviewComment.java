package com.example.gitverificationbot.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ReviewComment {
    @SerializedName("body")
    @JsonProperty("body")
    public String body;

    @SerializedName("commit_id")
    @JsonProperty("commit_id")
    public String commitId;

    @SerializedName("path")
    @JsonProperty("path")
    public String path;

    @SerializedName("position")
    @JsonProperty("position")
    public Integer pos;
    @JsonProperty("id")
    public Integer id;

    @SerializedName("side")
    @JsonProperty("side")
    public String side;

    @SerializedName("line")
    @JsonProperty("line")
    public Integer line;

    @SerializedName("in_reply_to")
    @JsonProperty("in_reply_to")
    public Integer repTo;

    public ReviewComment(String body) {
        this.body = body;
    }

    public ReviewComment() {
    }
}
