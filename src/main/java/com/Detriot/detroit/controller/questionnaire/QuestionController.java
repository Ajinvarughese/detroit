package com.Detriot.detroit.controller.questionnaire;

import com.Detriot.detroit.entity.questionnaire.Question;
import com.Detriot.detroit.service.questionnaire.QuestionService;
import lombok.AllArgsConstructor;
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

    //Get Single question by ID
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    // Get questions by questionnaire ID
    @GetMapping("/questionnaire/{questionnaire_id}")
    public ResponseEntity<List<Question>> getQuestionsByQuestionnaireId(@PathVariable Long questionnaireId) {
        return ResponseEntity.ok(questionService.getQuestionsByQuestionnaireId(questionnaireId));
    }

    // Add a question to a questionnaire
    @PostMapping("/questionnaire/{questionnaireId}")
    public ResponseEntity<Question> addQuestion(@PathVariable Long questionnaireId, @RequestBody Question question) {
        return ResponseEntity.ok(questionService.addQuestion(questionnaireId, question));
    }

    // Update an existing question
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        try {
            return ResponseEntity.ok(questionService.updateQuestion(id, question));
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
