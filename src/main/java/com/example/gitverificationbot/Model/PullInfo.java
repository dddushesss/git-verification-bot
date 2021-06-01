package com.example.gitverificationbot.Model;

import com.google.gson.annotations.SerializedName;


public class PullInfo {

    @SerializedName("commit")
    public Commit commit;

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }
}
