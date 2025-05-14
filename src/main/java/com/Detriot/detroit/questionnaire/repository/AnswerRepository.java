package com.Detriot.detroit.questionnaire.repository;

import com.Detriot.detroit.questionnaire.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUserId(Long id);
}
