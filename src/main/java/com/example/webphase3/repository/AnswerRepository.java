package com.example.webphase3.repository;

import com.example.webphase3.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByQuestionIdAndPlayerId(Long questionId, Long playerId);
}