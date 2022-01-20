package com.example.githubrepo.model;

public class Items {
    String name;
    String description;
    int stargazers_count;

    public Items(String name, String description, int stargazers_count) {
        this.name = name;
        this.description = description;
        this.stargazers_count = stargazers_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }
}
