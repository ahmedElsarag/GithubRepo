package com.example.githubrepo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GitRepo {
    @SerializedName("items")
    List<Items> items;

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}

