package com.Detroit.detroit.rule.service;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.rule.entity.Rule;
import com.Detroit.detroit.rule.repository.RuleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RuleService {
    private RuleRepository repository;

    // Get all rules
    public List<Rule> getAllRule() {
        return repository.findAll();
    }


    // Get rule by type
    public List<Rule> getRuleByType(LoanCategory category) {
        return repository.findByType(category);
    }

    // Add new rule
    public Rule addNewRule(Rule rule) {
        return repository.save(rule);
    }

    // Update existing rule
    public Rule updateRule(Rule updatedRule) {
        Rule existingRule = repository.findById(updatedRule.getId())
            .orElseThrow(() -> new EntityNotFoundException("Rule not found with id: " + updatedRule.getId()));

        existingRule.setType(updatedRule.getType());
        existingRule.setNace(updatedRule.getNace());
        existingRule.setSector(updatedRule.getSector());
        existingRule.setActivityNumber(updatedRule.getActivityNumber());
        existingRule.setActivity(updatedRule.getActivity());
        existingRule.setContributionType(updatedRule.getContributionType());
        existingRule.setDescription(updatedRule.getDescription());
        existingRule.setSubstantialCriteria(updatedRule.getSubstantialCriteria());
        existingRule.setClimateMitigationDNSH(updatedRule.getClimateMitigationDNSH());
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
