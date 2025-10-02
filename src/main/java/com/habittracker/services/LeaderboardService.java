package com.habittracker.services;

import com.habittracker.models.User;

import java.util.Comparator;
import java.util.List;

public class LeaderboardService {
    private final UserService userService = new UserService();

    public List<User> top10() {
        return userService.readAll().stream()
                .sorted(Comparator.comparingInt(User::getXp).reversed())
                .limit(10)
                .toList();
    }
}
