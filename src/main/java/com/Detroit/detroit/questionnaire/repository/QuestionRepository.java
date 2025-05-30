package com.Detroit.detroit.questionnaire.repository;

import com.Detroit.detroit.questionnaire.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuestionnaireId(Long questionnaireId);
    void deleteByQuestionnaireId(Long questionnaireId);

}
