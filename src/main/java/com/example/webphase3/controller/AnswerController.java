package com.example.webphase3.controller;

import com.example.webphase3.service.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<String> submitAnswer(
            @RequestParam Long questionId,
            @RequestParam Long playerId,
            @RequestParam int selectedOption) {
        String result = answerService.submitAnswer(questionId, playerId, selectedOption);
        return ResponseEntity.ok(result);
    }
}