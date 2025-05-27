package com.Detriot.detroit.questionnaire.service;

import com.Detriot.detroit.questionnaire.entity.Answer;
import com.Detriot.detroit.questionnaire.repository.AnswerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    private  final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository){
        this.answerRepository=answerRepository;
    }
    public List<Answer>getAllAnswers(){
        return answerRepository.findAll();
    }
    public Answer getAnswerById(Long id){
        return answerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Answer not found with id:"+id));
    }
    public List<Answer>getAnswerByUserId(Long userId){
        return answerRepository.findByUserId(userId);
    }
    public Answer saveAnswer(Answer answer){
        return answerRepository.save(answer);
    }
    public List<Answer> saveMultipleAnswers(List<Answer> answers){
        return answerRepository.saveAll(answers);
    }

    public void deleteAnswer(Long id){
        if (!answerRepository.existsById(id)){
            throw new EntityNotFoundException("Answer not found with id:"+id);
        }
        answerRepository.deleteById(id);
    }
}
