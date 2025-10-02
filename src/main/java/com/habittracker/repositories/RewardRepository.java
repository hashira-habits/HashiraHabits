package com.habittracker.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.habittracker.models.Reward;
import com.habittracker.utils.JsonFileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RewardRepository {
    private static final Path FILE = JsonFileUtil.resolveDataPath("rewards.json");

    public synchronized List<Reward> readAll() {
        try {
            List<Reward> items = JsonFileUtil.read(FILE, new TypeReference<>() {
            }, new ArrayList<>());
            if (items.isEmpty()) {
                items = initSample();
                saveAll(items);
            }
            return items;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void saveAll(List<Reward> rewards) {
        try {
            JsonFileUtil.write(FILE, rewards);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Reward> initSample() {
        List<Reward> list = new ArrayList<>();
        String[] names = { "Katana Skin", "Haori Cape", "Breathing Aura", "XP Booster", "Coin Pouch" };
        int[] prices = { 50, 60, 80, 100, 30 };
        for (int i = 0; i < names.length; i++) {
            Reward r = new Reward();
            r.setType("skin");
            r.setName(names[i]);
            r.setPriceCoins(prices[i]);
            r.setDescription("Unlock " + names[i]);
            list.add(r);
        }
        return list;
    }
}
