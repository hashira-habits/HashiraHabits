package com.habittracker.controllers;

import com.habittracker.dtos.UserDto;
import com.habittracker.models.User;
import com.habittracker.services.AuthService;
import com.habittracker.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService = new AuthService();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        try {
            User u = new User();
            u.setFullName(body.getOrDefault("fullName", ""));
            u.setEmail(body.getOrDefault("email", ""));
            u.setPassword(body.getOrDefault("password", "")); // Recommend hashing in real apps
            u.setFavoriteLearningArea(body.getOrDefault("favoriteLearningArea", ""));
            u.setAvatar(body.getOrDefault("avatar", "Tanjiro"));
            authService.register(u);
            return ResponseEntity.ok(UserService.toDto(u));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.getOrDefault("email", "");
        String password = body.getOrDefault("password", "");
        return authService.login(email, password)
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(Map.of(
                        "success", true,
                        "userId", u.getId(),
                        "fullName", u.getFullName(),
                        "avatar", u.getAvatar(),
                        "xp", u.getXp(),
                        "coins", u.getCoins())))
                .orElse(ResponseEntity.status(401).body(Map.of("success", false, "message", "Invalid credentials")));
    }
}
