package com.habittracker.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Habit {
    private String id;
    private String userId;
    private String name;
    private String frequency; // daily or weekly
    private int streak;
    private String lastDoneDate; // yyyy-MM-dd
    private LocalDateTime createdAt;

    public Habit() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    // Getters/setters
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public String getLastDoneDate() {
        return lastDoneDate;
    }

    public void setLastDoneDate(String lastDoneDate) {
        this.lastDoneDate = lastDoneDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
