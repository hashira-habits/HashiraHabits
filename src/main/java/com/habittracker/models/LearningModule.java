package com.habittracker.models;

import java.util.UUID;

public class LearningModule {
    private String id;
    private String userId;
    private String title;
    private String status; // Not Started / In Progress / Completed
    private int progressPercent;
    private int awardedXp;

    public LearningModule() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
    }

    public int getAwardedXp() {
        return awardedXp;
    }

    public void setAwardedXp(int awardedXp) {
        this.awardedXp = awardedXp;
    }
}
