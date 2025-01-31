package com.example.webphase3.service;

import com.example.webphase3.exception.AlreadyExistsException;
import com.example.webphase3.exception.NotFoundException;
import com.example.webphase3.model.Answer;
import com.example.webphase3.model.Question;
import com.example.webphase3.model.User;
import com.example.webphase3.repository.AnswerRepository;
import com.example.webphase3.repository.QuestionRepository;
import com.example.webphase3.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, UserRepository userRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public String submitAnswer(Long questionId, Long playerId, String selectedOption) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question not found"));

        Optional<Answer> existingAnswer = answerRepository.findByQuestionIdAndPlayerId(questionId, playerId);
        if (existingAnswer.isPresent()) {
            throw new AlreadyExistsException("You have already answered this question");
        }

        User user = userRepository.findById(playerId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        boolean isCorrect = question.getCorrectOption().equals(selectedOption);

        if (isCorrect) {
            user.setScore(user.getScore() + 3);
        } else {
            user.setScore(user.getScore() - 1);
        }
        userRepository.save(user);

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setPlayer(user);
        answer.setSelectedOption(selectedOption);
        answer.setCorrect(isCorrect);
        answerRepository.save(answer);

        return isCorrect ? "Correct answer" : "Wrong answer";
    }
}