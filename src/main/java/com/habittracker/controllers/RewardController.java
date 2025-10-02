package com.habittracker.controllers;

import com.habittracker.services.RewardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {
    private final RewardService rewardService = new RewardService();

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(rewardService.listAll());
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        String rewardId = body.get("rewardId");
        boolean ok = rewardService.purchase(userId, rewardId);
        if (!ok)
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Not enough coins"));
        return ResponseEntity.ok(Map.of("success", true));
    }
}
