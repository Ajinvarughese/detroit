package com.Detriot.detroit.repository;

import com.Detriot.detroit.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUserId(Long id);
}
