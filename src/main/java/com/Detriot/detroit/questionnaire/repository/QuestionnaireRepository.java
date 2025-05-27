package com.Detriot.detroit.questionnaire.repository;

import com.Detriot.detroit.enums.LoanCategory;
import com.Detriot.detroit.questionnaire.entity.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    Optional<Questionnaire> findByFormUrlId(String formUrlId);
    List<Questionnaire> findByLoanCategory(LoanCategory loanCategory);
}

