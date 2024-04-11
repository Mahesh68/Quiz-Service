package com.tech.quizservices.service;


import com.tech.quizservices.Feign.QuizInterface;
import com.tech.quizservices.dao.QuizRepo;
import com.tech.quizservices.model.QuestionWrapper;
import com.tech.quizservices.model.Quiz;
import com.tech.quizservices.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
//import java.util.List;
//import java.util.Optional;


@Service
public class QuizService {

    @Autowired
    private QuizRepo quizRepo;

//    @Autowired
//    private QuestionRepo questionRepo;

    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String title, String category, int numQ) {


        List<Integer> questions = quizInterface.generateQuestions(category, numQ).getBody();
//        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestion_ids(questions);

        quizRepo.save(quiz);

        return new ResponseEntity<>("Created quiz", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsofId(int id) {
        Optional<Quiz> quizId = quizRepo.findById(id);
        if (quizId.isPresent()) {
            return quizInterface.getQuestions(quizId.get().getQuestion_ids());
        }

        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        ResponseEntity<Integer> result = quizInterface.getScore(responses);

        return result;
    }
}
