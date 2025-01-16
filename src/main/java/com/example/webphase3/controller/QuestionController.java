package com.example.webphase3.controller;

import com.example.webphase3.model.Question;
import com.example.webphase3.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // Add a question
    @PostMapping
    public ResponseEntity<Question> addQuestion(
            @RequestBody Question question,
            @RequestParam Long creatorId,
            @RequestParam String categoryName) {
        Question savedQuestion = questionService.addQuestion(question, creatorId, categoryName);
        return ResponseEntity.ok(savedQuestion);
    }

    // Get questions by category
    @GetMapping
    public ResponseEntity<List<Question>> getQuestionsByCategory(@RequestParam(required = false) String category) {
        List<Question> questions;
        if (category != null) {
            questions = questionService.getQuestionsByCategory(category);
        } else {
            questions = questionService.getAllQuestions();
        }
        if (questions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questions);
    }

    // Get a random question
    @GetMapping("/random")
    public ResponseEntity<Question> getRandomQuestion() {
        Question question = questionService.getRandomQuestion();
        return ResponseEntity.ok(question);
    }

    // Delete a question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long id,
            @RequestParam Long creatorId) {
        questionService.deleteQuestion(id, creatorId);
        return ResponseEntity.noContent().build();
    }
}