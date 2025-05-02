package com.Detriot.detroit.repository.questionnaire;

import com.Detriot.detroit.entity.questionnaire.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice,Long> {


    List<Choice> findByQuestionId(Long questionId);
}
