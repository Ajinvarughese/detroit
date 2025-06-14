package com.Detroit.detroit.sf.service;

import com.Detroit.detroit.sf.entity.Feedback;
import com.Detroit.detroit.sf.repository.FeedbackRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class FeedbackService {

    private final FeedbackRepository feedbackRepo;

    public List<Feedback> getAllFeedback() {
        return feedbackRepo.findAll();
    }

    public Feedback addFeedback (Feedback feedback){
        return feedbackRepo.save(feedback);
    }

}

