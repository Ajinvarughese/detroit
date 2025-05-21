package com.Detriot.detroit.questionnaire.repository;

import com.Detriot.detroit.questionnaire.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice,Long> {
    List<Choice> findByQuestionId(Long questionId);
    void deleteByQuestionId(Long questionId);
}
