package com.habittracker.models;

import java.util.UUID;

public class Reward {
    private String id;
    private String type;
    private String name;
    private int priceCoins;
    private String description;

    public Reward() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriceCoins() {
        return priceCoins;
    }

    public void setPriceCoins(int priceCoins) {
        this.priceCoins = priceCoins;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
