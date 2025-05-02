package com.Detriot.detroit.controller.questionnaire;

import com.Detriot.detroit.entity.questionnaire.Answer;
import com.Detriot.detroit.service.questionnaire.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
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

    @PostMapping
    public ResponseEntity<Answer> createAnswer(@RequestBody Answer answer) {
        return ResponseEntity.ok(answerService.saveAnswer(answer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
