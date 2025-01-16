package com.example.webphase3.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option")
    private List<String> options;

    @Column(nullable = false)
    private int correctOption;

    @Column(nullable = false)
    private String difficulty;

    @Column(nullable = false)
    private String category;

    @ManyToMany
    @JoinTable(
            name = "related_questions",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "related_question_id")
    )
    private List<Question> relatedQuestions;

    @Column(nullable = false)
    private Long creatorId;
}