package com.tech.quizservices.dao;

import com.tech.quizservices.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface QuizRepo extends JpaRepository<Quiz, Integer> {
}
