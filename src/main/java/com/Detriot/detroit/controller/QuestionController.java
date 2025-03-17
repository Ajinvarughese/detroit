package com.Detriot.detroit.controller;

import com.Detriot.detroit.entity.Question;
import com.Detriot.detroit.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/question")
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    //Get all questions
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    //Get Single question by ID
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    /*
        TODO:
              * Get question by questionnaire
              * Add a new question to a questionnaire
              * Update an existing question
              * Delete a question

    */
}
