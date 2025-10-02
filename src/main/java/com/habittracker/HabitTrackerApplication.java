package com.habittracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HabitTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HabitTrackerApplication.class, args);
    }

    @Bean
    public CommandLineRunner bannerLogger() {
        return args -> {
            System.out.println("Habit Tracker started on http://localhost:8080/");
            System.out.println("JSON data files stored under ./data (created on first run)");
            System.out.println("Sample login -> email: test@local, password: test123");
        };
    }
}
















