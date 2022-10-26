package com.example.mad01;

public class TrainersModel {
    String name,price,time,url;

    public TrainersModel() {
    }

    public TrainersModel(String name, String price, String time, String url) {
        this.name = name;
        this.price = price;
        this.time = time;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
