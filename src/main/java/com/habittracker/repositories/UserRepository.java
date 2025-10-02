package com.habittracker.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.habittracker.models.ProgressEntry;
import com.habittracker.models.User;
import com.habittracker.utils.JsonFileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static final Path FILE = JsonFileUtil.resolveDataPath("users.json");

    public synchronized List<User> readAll() {
        try {
            List<User> users = JsonFileUtil.read(FILE, new TypeReference<>() {
            }, new ArrayList<>());
            if (users.isEmpty()) {
                users = initSample();
                saveAll(users);
            }
            return users;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void saveAll(List<User> users) {
        try {
            JsonFileUtil.write(FILE, users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Optional<User> findById(String id) {
        return readAll().stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public synchronized Optional<User> findByEmail(String email) {
        return readAll().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    private List<User> initSample() {
        List<User> list = new ArrayList<>();
        User u = new User();
        u.setFullName("Test User");
        u.setEmail("test@local");
        u.setPassword("test123");
        u.setAvatar("Tanjiro");
        u.setFavoriteLearningArea("Algorithms");
        u.setXp(150);
        u.setCoins(40);
        u.setLevel(0);
        ProgressEntry p = new ProgressEntry();
        p.setDate(java.time.LocalDate.now().minusDays(1).toString());
        p.setType("habit");
        p.setXpGained(10);
        p.setDetails("Read 30 mins");
        u.getProgressHistory().add(p);
        list.add(u);
        return list;
    }
}
