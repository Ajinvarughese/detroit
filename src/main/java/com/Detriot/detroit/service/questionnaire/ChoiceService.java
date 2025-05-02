package com.Detriot.detroit.service.questionnaire;

import com.Detriot.detroit.entity.questionnaire.Choice;
import com.Detriot.detroit.repository.questionnaire.ChoiceRepository;
import com.Detriot.detroit.repository.questionnaire.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChoiceService {
    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;
    //Get all choices
    public List<Choice> getAllChoices(){

        return choiceRepository.findAll();
    }
    //Get choice by id

    public Choice getChoiceById(Long id){
        return choiceRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Choice not found with id:"+id));
    }

    //Get choices by question id
    public List<Choice> getChoicesByQuestionId(Long questionId){
        return choiceRepository.findByQuestionId(questionId);
    }

    //add choice
    public Choice addChoice(Choice choice){
        return choiceRepository.save(choice);
    }


    //Delete choice
    public void deleteChoice(Long id) {
        if (!choiceRepository.existsById(id)) {
            throw new EntityNotFoundException("Choice not found with id: " + id);
        }
        choiceRepository.deleteById(id);
    }
}
