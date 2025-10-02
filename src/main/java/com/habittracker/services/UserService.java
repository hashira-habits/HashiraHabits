package com.habittracker.services;

import com.habittracker.dtos.UserDto;
import com.habittracker.models.ProgressEntry;
import com.habittracker.models.User;
import com.habittracker.repositories.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public Optional<User> getById(String id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        List<User> all = userRepository.readAll();
        all.removeIf(u -> u.getId().equals(user.getId()));
        all.add(user);
        userRepository.saveAll(all);
        return user;
    }

    public void delete(String id) {
        List<User> all = userRepository.readAll();
        all.removeIf(u -> u.getId().equals(id));
        userRepository.saveAll(all);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void addXpAndCoins(User user, int xp, int coins, String type, String details, String date) {
        user.setXp(Math.max(0, user.getXp() + xp));
        user.setCoins(Math.max(0, user.getCoins() + coins));
        user.setLevel(user.getXp() / 1000);
        ProgressEntry entry = new ProgressEntry();
        entry.setDate(date);
        entry.setType(type);
        entry.setXpGained(xp);
        entry.setDetails(details);
        user.getProgressHistory().add(entry);
        save(user);
    }

    public List<User> readAll() {
        return userRepository.readAll();
    }

    public List<User> topUsers() {
        return readAll().stream()
                .sorted(Comparator.comparingInt(User::getXp).reversed())
                .limit(10)
                .toList();
    }

    public static UserDto toDto(User u) {
        UserDto d = new UserDto();
        d.id = u.getId();
        d.fullName = u.getFullName();
        d.email = u.getEmail();
        d.avatar = u.getAvatar();
        d.favoriteLearningArea = u.getFavoriteLearningArea();
        d.xp = u.getXp();
        d.coins = u.getCoins();
        d.level = u.getLevel();
        return d;
    }
}
