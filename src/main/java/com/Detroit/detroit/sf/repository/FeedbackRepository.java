package com.Detroit.detroit.sf.repository;

import com.Detroit.detroit.sf.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {}

