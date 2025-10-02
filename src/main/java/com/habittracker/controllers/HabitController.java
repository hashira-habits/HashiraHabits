package com.habittracker.controllers;

import com.habittracker.models.Habit;
import com.habittracker.services.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/habits")
public class HabitController {
    private final HabitService habitService = new HabitService();

    @GetMapping
    public ResponseEntity<?> list(@RequestParam String userId) {
        List<Habit> list = habitService.listByUser(userId);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Habit habit) {
        if (habit.getUserId() == null)
            return ResponseEntity.badRequest().body(Map.of("message", "userId required"));
        if (habit.getFrequency() == null)
            habit.setFrequency("daily");
        return ResponseEntity.ok(habitService.create(habit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Habit habit) {
        return habitService.findById(id).map(h -> {
            h.setName(habit.getName() != null ? habit.getName() : h.getName());
            h.setFrequency(habit.getFrequency() != null ? habit.getFrequency() : h.getFrequency());
            return ResponseEntity.ok(habitService.update(h));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        habitService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/mark")
    public ResponseEntity<?> mark(@PathVariable String id, @RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        try {
            var result = habitService.markDone(id, userId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
