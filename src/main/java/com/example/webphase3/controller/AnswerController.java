package com.example.webphase3.controller;

import com.example.webphase3.exception.AlreadyExistsException;
import com.example.webphase3.exception.NotFoundException;
import com.example.webphase3.service.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<String> submitAnswer(
            @RequestParam Long questionId,
            @RequestParam Long playerId,
            @RequestParam String selectedOption) {
        try {
            String result = answerService.submitAnswer(questionId, playerId, selectedOption);
            return ResponseEntity.ok(result);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}