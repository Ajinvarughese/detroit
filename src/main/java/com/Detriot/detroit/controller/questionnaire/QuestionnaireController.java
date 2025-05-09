package com.Detriot.detroit.controller.questionnaire;

import com.Detriot.detroit.entity.questionnaire.Questionnaire;
import com.Detriot.detroit.service.questionnaire.QuestionnaireService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/questionnaire")

public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    //Get all questions
    @GetMapping
    public ResponseEntity<List<Questionnaire>> getAllQuestionnaire(){
       return ResponseEntity.ok(questionnaireService.getAllQuestionnaire());
    }

    //Get questionnaire by ID
    @GetMapping("/{id}")
    public ResponseEntity<Questionnaire> getQuestionnaireById(@PathVariable Long id){
        return ResponseEntity.ok(questionnaireService.getQuestionnaireById(id));
    }

    // Create a new questionnaire
    @PostMapping
    public ResponseEntity<Questionnaire> addQuestionnaire(@RequestBody Questionnaire questionnaire) {
        return ResponseEntity.ok(questionnaireService.addQuestionnaire(questionnaire));
    }

    // Update an existing questionnaire
    @PutMapping("/{id}")
    public ResponseEntity<Questionnaire> updateQuestionnaire(@PathVariable Long id ,@RequestBody Questionnaire questionnaire) {
        try {
            return ResponseEntity.ok(questionnaireService.updateQuestionnaire(id ,questionnaire));
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    // deleting an existing questionnaire
    @DeleteMapping("/{id}")
    public ResponseEntity<Questionnaire> deleteQuestionnaire(@PathVariable Long id){
        try {
            questionnaireService.deleteQuestionnaire(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }
}

