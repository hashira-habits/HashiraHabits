package com.habittracker.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.habittracker.models.LearningModule;
import com.habittracker.utils.JsonFileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LearningRepository {
    private static final Path FILE = JsonFileUtil.resolveDataPath("learning.json");

    public synchronized List<LearningModule> readAll() {
        try {
            List<LearningModule> items = JsonFileUtil.read(FILE, new TypeReference<>() {
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

    public synchronized void saveAll(List<LearningModule> modules) {
        try {
            JsonFileUtil.write(FILE, modules);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized List<LearningModule> findByUserId(String userId) {
        return readAll().stream().filter(m -> userId.equals(m.getUserId())).collect(Collectors.toList());
    }

    private List<LearningModule> initSample() {
        List<LearningModule> list = new ArrayList<>();
        String defaultUserId = null;
        try {
            defaultUserId = new UserRepository().readAll().get(0).getId();
        } catch (Exception ignored) {
        }
        if (defaultUserId != null) {
            LearningModule m1 = new LearningModule();
            m1.setUserId(defaultUserId);
            m1.setTitle("Intro to Algorithms");
            m1.setStatus("Not Started");
            m1.setProgressPercent(0);
            m1.setAwardedXp(50);
            list.add(m1);

            LearningModule m2 = new LearningModule();
            m2.setUserId(defaultUserId);
            m2.setTitle("Data Structures Deep Dive");
            m2.setStatus("In Progress");
            m2.setProgressPercent(40);
            m2.setAwardedXp(120);
            list.add(m2);

            LearningModule m3 = new LearningModule();
            m3.setUserId(defaultUserId);
            m3.setTitle("System Design Basics");
            m3.setStatus("Not Started");
            m3.setProgressPercent(0);
            m3.setAwardedXp(200);
            list.add(m3);
        }
        return list;
    }
}
