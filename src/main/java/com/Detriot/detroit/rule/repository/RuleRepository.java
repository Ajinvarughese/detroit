package com.Detriot.detroit.rule.repository;

import com.Detriot.detroit.enums.RuleCategory;
import com.Detriot.detroit.rule.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
    Optional<Rule> findRuleByRuleCategory(RuleCategory category);
}
