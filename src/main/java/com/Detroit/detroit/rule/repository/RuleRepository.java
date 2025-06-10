package com.Detroit.detroit.rule.repository;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.rule.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
    List<Rule> findByType(LoanCategory category);
}
