package com.habittracker.services;

import com.habittracker.models.Habit;
import com.habittracker.repositories.HabitRepository;
import com.habittracker.utils.DateUtil;

import java.util.List;
import java.util.Optional;

public class HabitService {
    private final HabitRepository habitRepository = new HabitRepository();
    private final UserService userService = new UserService();

    public List<Habit> listByUser(String userId) {
        return habitRepository.findByUserId(userId);
    }

    public Habit create(Habit habit) {
        List<Habit> all = habitRepository.readAll();
        all.add(habit);
        habitRepository.saveAll(all);
        return habit;
    }

    public Optional<Habit> findById(String id) {
        return habitRepository.readAll().stream().filter(h -> h.getId().equals(id)).findFirst();
    }

    public Habit update(Habit habit) {
        List<Habit> all = habitRepository.readAll();
        all.removeIf(h -> h.getId().equals(habit.getId()));
        all.add(habit);
        habitRepository.saveAll(all);
        return habit;
    }

    public void delete(String id) {
        List<Habit> all = habitRepository.readAll();
        all.removeIf(h -> h.getId().equals(id));
        habitRepository.saveAll(all);
    }

    public Result markDone(String habitId, String userId) {
        Habit habit = findById(habitId).orElseThrow(() -> new IllegalArgumentException("Habit not found"));
        if (!habit.getUserId().equals(userId))
            throw new IllegalArgumentException("Forbidden");

        if (DateUtil.isToday(habit.getLastDoneDate())) {
            throw new IllegalStateException("Already marked today");
        }

        int streak = habit.getStreak();
        if (DateUtil.isConsecutiveDay(habit.getLastDoneDate())) {
            streak += 1;
        } else {
            streak = 1;
        }
        habit.setStreak(streak);
        habit.setLastDoneDate(DateUtil.todayString());
        update(habit);

        var user = userService.getById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        int xp = 10;
        int coins = 5;
        if (streak >= 7)
            xp += 20;
        userService.addXpAndCoins(user, xp, coins, "habit", habit.getName(), DateUtil.todayString());

        Result r = new Result();
        r.habit = habit;
        r.userSummary = com.habittracker.services.UserService.toDto(user);
        return r;
    }

    public static class Result {
        public Habit habit;
        public com.habittracker.dtos.UserDto userSummary;
    }
}
