package com.Detriot.detroit.rule.service;

import com.Detriot.detroit.enums.RuleCategory;
import com.Detriot.detroit.rule.entity.Rule;
import com.Detriot.detroit.rule.repository.RuleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleService {
    private RuleRepository repository;

    // Get all rules
    public List<Rule> getAllRule() {
        return repository.findAll();
    }

    // Get rules by id
    public Rule getRuleById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rule not found with id: "+id));
    }

    // Get rule by type
    public Rule getRuleByType(RuleCategory category) {
        return repository.findRuleByRuleCategory(category)
                .orElseThrow(() -> new EntityNotFoundException("Rule not found with type: "+category));
    }

    // Add new rule
    public Rule addNewRule(Rule rule) {
        return repository.save(rule);
    }

    // Update existing rule
    public Rule updateRule(Rule updatedRule) {
        Rule existingRule = repository.findById(updatedRule.getId())
            .orElseThrow(() -> new EntityNotFoundException("Rule not found with id: " + updatedRule.getId()));

        existingRule.setRuleCategory(updatedRule.getRuleCategory());
        existingRule.setNace(updatedRule.getNace());
        existingRule.setSector(updatedRule.getSector());
        existingRule.setActivityNumber(updatedRule.getActivityNumber());
        existingRule.setActivityName(updatedRule.getActivityName());
        existingRule.setContributionType(updatedRule.getContributionType());
        existingRule.setDescription(updatedRule.getDescription());
        existingRule.setSubstantialCriteria(updatedRule.getSubstantialCriteria());
        existingRule.setClimateMitigation(updatedRule.getClimateMitigation());
        existingRule.setCircularEconomyDNSH(updatedRule.getCircularEconomyDNSH());
        existingRule.setClimateAdaptationDNSH(updatedRule.getClimateAdaptationDNSH());
        existingRule.setWaterDNSH(updatedRule.getWaterDNSH());
        existingRule.setPollutionPreventionDNSH(updatedRule.getPollutionPreventionDNSH());
        existingRule.setBiodiversityDNSH(updatedRule.getBiodiversityDNSH());
        existingRule.setFootNotes(updatedRule.getFootNotes());

        return repository.save(existingRule);
    }

    // Delete rules
    public void deleteRule(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rule not found with id: "+id));
        repository.deleteById(id);
    }

}
