package com.habittracker.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.habittracker.models.Habit;
import com.habittracker.utils.JsonFileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HabitRepository {
    private static final Path FILE = JsonFileUtil.resolveDataPath("habits.json");

    public synchronized List<Habit> readAll() {
        try {
            List<Habit> items = JsonFileUtil.read(FILE, new TypeReference<>() {
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

    public synchronized void saveAll(List<Habit> habits) {
        try {
            JsonFileUtil.write(FILE, habits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized List<Habit> findByUserId(String userId) {
        return readAll().stream().filter(h -> h.getUserId().equals(userId)).collect(Collectors.toList());
    }

    private List<Habit> initSample() {
        List<Habit> list = new ArrayList<>();
        // Link to first user if exists
        String defaultUserId = null;
        try {
            defaultUserId = new UserRepository().readAll().get(0).getId();
        } catch (Exception ignored) {
        }

        if (defaultUserId != null) {
            Habit h1 = new Habit();
            h1.setUserId(defaultUserId);
            h1.setName("Read 30 mins");
            h1.setFrequency("daily");
            h1.setStreak(1);
            list.add(h1);

            Habit h2 = new Habit();
            h2.setUserId(defaultUserId);
            h2.setName("Solve 3 DSA problems");
            h2.setFrequency("weekly");
            h2.setStreak(0);
            list.add(h2);

            Habit h3 = new Habit();
            h3.setUserId(defaultUserId);
            h3.setName("Practice breathing exercises");
            h3.setFrequency("daily");
            h3.setStreak(0);
            list.add(h3);
        }
        return list;
    }
}
