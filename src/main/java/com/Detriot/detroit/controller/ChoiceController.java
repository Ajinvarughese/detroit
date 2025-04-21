package com.Detriot.detroit.controller;

import com.Detriot.detroit.entity.Choice;
import com.Detriot.detroit.service.ChoiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/choices")
@AllArgsConstructor
public class ChoiceController {
    private final ChoiceService choiceService;
    @GetMapping
    public ResponseEntity<List<Choice>>getAllChoices() {
        return ResponseEntity.ok(choiceService.getAllChoices());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Choice>getChoiceById(@PathVariable Long id){
        return ResponseEntity.ok(choiceService.getChoiceById(id));
    }
    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Choice>> getChoicesByQuestionId(@PathVariable Long questionId) {
        return ResponseEntity.ok(choiceService.getChoicesByQuestionId(questionId));
    }
    @PostMapping
    public ResponseEntity<Choice>createChoice(@RequestBody Choice choice){
        return ResponseEntity.ok(choiceService.saveChoice(choice));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChoice(@PathVariable Long id) {
        choiceService.deleteChoice(id);
        return ResponseEntity.noContent().build();
    }
}

