package com.example.finalmyfinance;

public class MainModel {

    String amount,name,note,url;

    public MainModel() {
    }

    public MainModel(String amount, String name, String note, String url) {
        this.amount = amount;
        this.name = name;
        this.note = note;
        this.url = url;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
