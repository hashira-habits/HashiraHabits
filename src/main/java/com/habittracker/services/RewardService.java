package com.habittracker.services;

import com.habittracker.models.Reward;
import com.habittracker.models.User;
import com.habittracker.repositories.RewardRepository;

import java.util.List;

public class RewardService {
    private final RewardRepository rewardRepository = new RewardRepository();
    private final UserService userService = new UserService();

    public List<Reward> listAll() {
        return rewardRepository.readAll();
    }

    public boolean purchase(String userId, String rewardId) {
        User user = userService.getById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Reward reward = listAll().stream().filter(r -> r.getId().equals(rewardId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Reward not found"));
        if (user.getCoins() < reward.getPriceCoins())
            return false;
        user.setCoins(user.getCoins() - reward.getPriceCoins());
        if (!user.getUnlockedRewards().contains(reward.getName())) {
            user.getUnlockedRewards().add(reward.getName());
        }
        userService.save(user);
        return true;
    }
}
