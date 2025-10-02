package com.habittracker.services;

import com.habittracker.models.LearningModule;
import com.habittracker.repositories.LearningRepository;
import com.habittracker.utils.DateUtil;

import java.util.List;
import java.util.Optional;

public class LearningService {
    private final LearningRepository repo = new LearningRepository();
    private final UserService userService = new UserService();

    public List<LearningModule> listByUser(String userId) {
        return repo.findByUserId(userId);
    }

    public Optional<LearningModule> findById(String id) {
        return repo.readAll().stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public LearningModule update(LearningModule m) {
        List<LearningModule> all = repo.readAll();
        all.removeIf(x -> x.getId().equals(m.getId()));
        all.add(m);
        repo.saveAll(all);
        return m;
    }

    public LearningModule complete(String moduleId, String userId) {
        LearningModule m = findById(moduleId).orElseThrow(() -> new IllegalArgumentException("Module not found"));
        if (!userId.equals(m.getUserId()))
            throw new IllegalArgumentException("Forbidden");
        m.setStatus("Completed");
        m.setProgressPercent(100);
        update(m);

        var user = userService.getById(userId).orElseThrow();
        userService.addXpAndCoins(user, m.getAwardedXp(), 0, "learning", m.getTitle(), DateUtil.todayString());
        return m;
    }
}
