package com.habittracker.controllers;

import com.habittracker.services.LeaderboardService;
import com.habittracker.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LeaderboardController {
    private final LeaderboardService leaderboardService = new LeaderboardService();
    private final UserService userService = new UserService();

    @GetMapping("/leaderboard")
    public ResponseEntity<?> leaderboard() {
        var users = leaderboardService.top10().stream().map(UserService::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/progress")
    public ResponseEntity<?> progress(@RequestParam String userId) {
        return userService.getById(userId)
                .<ResponseEntity<?>>map(u -> {
                    Map<String, Object> data = new HashMap<>();
                    List<Map<String, Object>> xpOverTime = u.getProgressHistory().stream().map(p -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("date", p.getDate());
                        m.put("xp", p.getXpGained());
                        return m;
                    }).collect(Collectors.toList());
                    data.put("xpOverTime", xpOverTime);
                    data.put("streakHistory", List.of());
                    long completed = u.getProgressHistory().stream().filter(p -> p.getType().equals("learning"))
                            .count();
                    data.put("completedVsPending",
                            Map.of("completed", completed, "pending", Math.max(0, 10 - completed)));
                    return ResponseEntity.ok(data);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
