package com.Detriot.detroit.repository.questionnaire;

import com.Detriot.detroit.entity.questionnaire.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

}

