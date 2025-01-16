package com.example.webphase3.repository;

import com.example.webphase3.model.Category;
import com.example.webphase3.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategory(Category category); // Fetch questions by category
}
