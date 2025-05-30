package com.Detroit.detroit.questionnaire.controller;

import com.Detroit.detroit.dto.AnswerDTO;
import com.Detroit.detroit.questionnaire.entity.Answer;

import com.Detroit.detroit.questionnaire.service.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/answers")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping
    public ResponseEntity<List<Answer>> getAllAnswers() {
        return ResponseEntity.ok(answerService.getAllAnswers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswersById(@PathVariable Long id) {
        return ResponseEntity.ok(answerService.getAnswerById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Answer>>
    getAnswerByUser(@PathVariable Long userId) {
        return
                ResponseEntity.ok(answerService.getAnswerByUserId(userId));
    }

    @PostMapping("/save")
    public ResponseEntity<Answer> createAnswer(@RequestBody Answer answer) {
        return ResponseEntity.ok(answerService.saveAnswer(answer));
    }

    @PostMapping
    public ResponseEntity<List<Answer>> createAnswers(@RequestBody AnswerDTO answers) {
        return ResponseEntity.ok(answerService.saveMultipleAnswers(answers));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
