package com.habittracker.controllers;

import com.habittracker.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService = new UserService();

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return userService.getById(id)
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(com.habittracker.services.UserService.toDto(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Map<String, Object> body) {
        return userService.getById(id).map(u -> {
            if (body.containsKey("fullName"))
                u.setFullName(String.valueOf(body.get("fullName")));
            if (body.containsKey("email"))
                u.setEmail(String.valueOf(body.get("email")));
            if (body.containsKey("avatar"))
                u.setAvatar(String.valueOf(body.get("avatar")));
            if (body.containsKey("favoriteLearningArea"))
                u.setFavoriteLearningArea(String.valueOf(body.get("favoriteLearningArea")));
            if (body.containsKey("xp"))
                u.setXp(((Number) body.get("xp")).intValue());
            if (body.containsKey("coins"))
                u.setCoins(((Number) body.get("coins")).intValue());
            if (body.containsKey("level"))
                u.setLevel(((Number) body.get("level")).intValue());
            if (body.containsKey("progressHistory")) {
                // naive replace; client should send correctly shaped objects
                u.setProgressHistory(new java.util.ArrayList<>());
            }
            userService.save(u);
            return ResponseEntity.ok(com.habittracker.services.UserService.toDto(u));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
