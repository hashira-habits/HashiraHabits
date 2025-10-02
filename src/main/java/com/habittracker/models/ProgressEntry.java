package com.habittracker.models;

public class ProgressEntry {
    private String date; // yyyy-MM-dd
    private String type; // habit or learning
    private int xpGained;
    private String details;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getXpGained() {
        return xpGained;
    }

    public void setXpGained(int xpGained) {
        this.xpGained = xpGained;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
