package com.Detriot.detroit.questionnaire.repository;

import com.Detriot.detroit.questionnaire.entity.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

}

