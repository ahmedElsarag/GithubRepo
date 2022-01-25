package com.example.githubrepo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GitRepo {
    private Throwable error;
    @SerializedName("items")
    List<Items> items;

    public GitRepo(Throwable error) {
        this.error = error;
        this.items = null;
    }
    public List<Items> getItems() {
        return items;
    }
    public void setItems(List<Items> items) {
        this.items = items;
    }

    public Throwable getError() {
        return error;
    }
}

