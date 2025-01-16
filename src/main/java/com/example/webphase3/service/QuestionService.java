package com.example.webphase3.service;

import com.example.webphase3.exception.NotFoundException;
import com.example.webphase3.exception.UnauthorizedException;
import com.example.webphase3.model.Category;
import com.example.webphase3.model.Question;
import com.example.webphase3.repository.CategoryRepository;
import com.example.webphase3.repository.QuestionRepository;
import com.example.webphase3.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Question addQuestion(Question question, Long creatorId, String categoryName) {
        userRepository.findById(creatorId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        question.setCreatorId(creatorId);
        question.setCategory(category);

        return questionRepository.save(question);
    }

    public List<Question> getQuestionsByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        return questionRepository.findByCategory(category);
    }

    public Question getRandomQuestion() {
        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            throw new NotFoundException("No questions found");
        }
        Random random = new Random();
        return questions.get(random.nextInt(questions.size()));
    }

    public void deleteQuestion(Long id, Long creatorId) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found"));

        if (!question.getCreatorId().equals(creatorId)) {
            throw new UnauthorizedException("You are not authorized to delete this question");
        }

        questionRepository.deleteById(id);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}