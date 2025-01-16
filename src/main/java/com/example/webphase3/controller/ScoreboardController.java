package com.example.webphase3.controller;

import com.example.webphase3.controller.Dto.UserScoreDto;
import com.example.webphase3.model.User;
import com.example.webphase3.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/scoreboard")
public class ScoreboardController {

    private final UserRepository userRepository;

    public ScoreboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserScoreDto>> getScoreboard() {
        List<User> players = userRepository.findByRole("player");
        List<UserScoreDto> scoreboard = players.stream()
                .sorted(Comparator.comparingInt(User::getScore).reversed())
                .map(user -> new UserScoreDto(user.getUsername(), user.getScore()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(scoreboard);
    }
}