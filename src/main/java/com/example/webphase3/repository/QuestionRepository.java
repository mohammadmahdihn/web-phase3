package com.example.webphase3.repository;

import com.example.webphase3.model.Category;
import com.example.webphase3.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategory(Category category);

    @Query("SELECT q FROM Question q " +
            "WHERE q.creatorId IN :followingIds " +
            "AND q.id NOT IN (SELECT a.question.id FROM Answer a WHERE a.player.id = :userId) " +
            "ORDER BY q.createdAt DESC")
    List<Question> findFeedQuestions(@Param("userId") Long userId, @Param("followingIds") List<Long> followingIds);
}
