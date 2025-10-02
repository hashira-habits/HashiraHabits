package com.habittracker.services;

import com.habittracker.models.User;

import java.util.List;
import java.util.Optional;

public class AuthService {
    private final UserService userService = new UserService();

    public Optional<User> login(String email, String password) {
        return userService.findByEmail(email)
                .filter(u -> u.getPassword() != null && u.getPassword().equals(password));
    }

    public User register(User user) {
        // NOTE: For real apps, hash the password and use JWTs/sessions.
        List<User> all = userService.readAll();
        if (all.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()))) {
            throw new IllegalArgumentException("Email already registered");
        }
        all.add(user);
        new com.habittracker.repositories.UserRepository().saveAll(all);
        return user;
    }
}
