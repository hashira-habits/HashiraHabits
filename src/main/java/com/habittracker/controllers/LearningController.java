package com.habittracker.controllers;

import com.habittracker.services.LearningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/learning")
public class LearningController {
    private final LearningService learningService = new LearningService();

    @GetMapping
    public ResponseEntity<?> list(@RequestParam String userId) {
        return ResponseEntity.ok(learningService.listByUser(userId));
    }

    @PostMapping("/{moduleId}/complete")
    public ResponseEntity<?> complete(@PathVariable String moduleId, @RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        try {
            return ResponseEntity.ok(learningService.complete(moduleId, userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        }
    }
}
