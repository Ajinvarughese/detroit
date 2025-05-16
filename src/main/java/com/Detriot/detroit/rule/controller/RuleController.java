package com.Detriot.detroit.rule.controller;

import com.Detriot.detroit.enums.RuleCategory;
import com.Detriot.detroit.rule.entity.Rule;
import com.Detriot.detroit.rule.service.RuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rules")
public class RuleController {
    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    //Get all rules
    @GetMapping
    public ResponseEntity<List<Rule>> getAllQuestions() {
        return ResponseEntity.ok(ruleService.getAllRule());
    }

    //Get rule by ID
    @GetMapping("/{id}")
    public ResponseEntity<Rule> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(ruleService.getRuleById(id));
    }

    //Get rule by type
    @GetMapping("/{type}")
    public ResponseEntity<Rule> getRulesByType(@PathVariable RuleCategory category) {
        return ResponseEntity.ok(ruleService.getRuleByType(category));
    }

    // Add a new rule
    @PostMapping
    public ResponseEntity<Rule> addQuestion(@RequestBody Rule rule) {
        return ResponseEntity.ok(ruleService.addNewRule(rule));
    }

    // Update an existing question
    @PutMapping
    public ResponseEntity<Rule> updateQuestion(@RequestBody Rule rule) {
        try {
            return ResponseEntity.ok(ruleService.updateRule(rule));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        try {
            ruleService.deleteRule(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
