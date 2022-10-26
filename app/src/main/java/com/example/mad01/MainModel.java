package com.example.mad01;

public class MainModel {
    String duration,name,sets,url;

    public MainModel() {
    }

    public MainModel(String duration, String name, String sets, String url) {
        this.duration = duration;
        this.name = name;
        this.sets = sets;
        this.url = url;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
