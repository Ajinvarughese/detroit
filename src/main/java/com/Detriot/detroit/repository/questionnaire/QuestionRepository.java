package com.Detriot.detroit.repository.questionnaire;

import com.Detriot.detroit.entity.questionnaire.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuestionnaireId(Long questionnaireId);
}
