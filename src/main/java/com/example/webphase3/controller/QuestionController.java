package com.example.webphase3.controller;

import com.example.webphase3.exception.NotFoundException;
import com.example.webphase3.model.Question;
import com.example.webphase3.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<?> addQuestion(
            @RequestBody Question question,
            @RequestParam Long creatorId,
            @RequestParam String categoryName) {
        try {
            Question savedQuestion = questionService.addQuestion(question, creatorId, categoryName);
            return ResponseEntity.ok(savedQuestion);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getQuestionsByCategory(@RequestParam(required = false) String category) {
        try {
            List<Question> questions;
            if (category != null) {
                questions = questionService.getQuestionsByCategory(category);
            } else {
                questions = questionService.getAllQuestions();
            }
            return ResponseEntity.ok(questions);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomQuestion() {
        try {
            Question question = questionService.getRandomQuestion();
            return ResponseEntity.ok(question);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long id,
            @RequestParam Long creatorId) {
        questionService.deleteQuestion(id, creatorId);
        return ResponseEntity.noContent().build();
    }
}