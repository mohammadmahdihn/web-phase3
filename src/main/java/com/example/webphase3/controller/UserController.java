package com.example.webphase3.controller;

import com.example.webphase3.model.Question;
import com.example.webphase3.model.User;
import com.example.webphase3.repository.QuestionRepository;
import com.example.webphase3.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public UserController(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            user.setScore(userDetails.getScore());
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<String> followUser(
            @PathVariable String username,
            @RequestParam String followerUsername) {
        User userToFollow = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        if (follower.getUsername().equals(username)) {
            return ResponseEntity.badRequest().body("You cannot follow yourself");
        }

        if (follower.getFollowing().contains(userToFollow)) {
            return ResponseEntity.badRequest().body("You are already following this user");
        }

        follower.getFollowing().add(userToFollow);
        userRepository.save(follower);

        return ResponseEntity.ok("User followed successfully");
    }

    @GetMapping("/{userId}/feed")
    public ResponseEntity<List<Question>> getFeed(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Long> followingIds = user.getFollowing().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        List<Question> feedQuestions = questionRepository.findFeedQuestions(userId, followingIds);

        return ResponseEntity.ok(feedQuestions);
    }
}