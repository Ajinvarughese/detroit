package com.Detriot.detroit.questionnaire.controller;

import com.Detriot.detroit.dto.QuestionnaireDTO;
import com.Detriot.detroit.questionnaire.entity.Questionnaire;
import com.Detriot.detroit.questionnaire.service.QuestionnaireService;
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

    // Get complete questionnaire
    @GetMapping("/u/form/{questionnaireId}")
    public ResponseEntity<QuestionnaireDTO> getCompleteQuestionnaire(@PathVariable Long questionnaireId){
        return ResponseEntity.ok(questionnaireService.getCompleteQuestionnaire(questionnaireId));
    }

    //Get questionnaire by form url id
    @GetMapping("/form/{form_url_id}")
    public ResponseEntity<Questionnaire> getQuestionnaireByFormUrlId(@PathVariable String form_url_id){
        return ResponseEntity.ok(questionnaireService.getQuestionnaireByFormUrlId(form_url_id));
    }

    // Create a new questionnaire
    @PostMapping
    public ResponseEntity<Questionnaire> addQuestionnaire(@RequestBody Questionnaire questionnaire) {
        return ResponseEntity.ok(questionnaireService.addQuestionnaire(questionnaire));
    }

    // Update an existing questionnaire
    @PutMapping
    public ResponseEntity<Questionnaire> updateQuestionnaire(@RequestBody Questionnaire questionnaire) {
        try {
            return ResponseEntity.ok(questionnaireService.updateQuestionnaire(questionnaire));
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

