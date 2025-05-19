package com.Detriot.detroit.questionnaire.controller;

import com.Detriot.detroit.questionnaire.entity.Question;
import com.Detriot.detroit.questionnaire.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/question")

public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    //Get all questions
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    //Get a Single question by ID
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    // Get questions by questionnaire ID
    @GetMapping("/questionnaire/{questionnaireId}")
    public ResponseEntity<List<Question>> getQuestionsByQuestionnaireId(@PathVariable Long questionnaireId) {
        return ResponseEntity.ok(questionService.getQuestionsByQuestionnaireId(questionnaireId));
    }

    // Add a question to a questionnaire
    @PostMapping
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(questionService.addQuestion(question));
    }

    // Update an existing question
    @PutMapping
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        try {
            return ResponseEntity.ok(questionService.updateQuestion(question));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
