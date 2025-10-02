package com.habittracker.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private String id;
    private String fullName;
    private String email;
    private String password; // In real apps, store a hashed password
    private String avatar;
    private String favoriteLearningArea;
    private int xp;
    private int coins;
    private int level;
    private List<String> unlockedRewards;
    private LocalDateTime createdAt;
    private List<ProgressEntry> progressHistory;

    public User() {
        this.id = UUID.randomUUID().toString();
        this.unlockedRewards = new ArrayList<>();
        this.progressHistory = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFavoriteLearningArea() {
        return favoriteLearningArea;
    }

    public void setFavoriteLearningArea(String favoriteLearningArea) {
        this.favoriteLearningArea = favoriteLearningArea;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<String> getUnlockedRewards() {
        return unlockedRewards;
    }

    public void setUnlockedRewards(List<String> unlockedRewards) {
        this.unlockedRewards = unlockedRewards;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ProgressEntry> getProgressHistory() {
        return progressHistory;
    }

    public void setProgressHistory(List<ProgressEntry> progressHistory) {
        this.progressHistory = progressHistory;
    }
}
