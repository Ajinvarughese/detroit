package com.Detroit.detroit.questionnaire.service;

import com.Detroit.detroit.questionnaire.entity.Choice;
import com.Detroit.detroit.questionnaire.repository.ChoiceRepository;
import com.Detroit.detroit.questionnaire.repository.QuestionRepository;

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

    public Choice updateChoice(Choice updatedChoice){
        Choice existing = choiceRepository.findById(updatedChoice.getId())
                .orElseThrow(() -> new EntityNotFoundException("Choice not found with id: " + updatedChoice.getId()));
        existing.setChoiceText(updatedChoice.getChoiceText());
        existing.setScore(updatedChoice.getScore());
        return choiceRepository.save(existing);
    }


    //Delete choice
    public void deleteChoice(Long id) {
        if (!choiceRepository.existsById(id)) {
            throw new EntityNotFoundException("Choice not found with id: " + id);
        }
        choiceRepository.deleteById(id);
    }

    // Delete choices by question id
    public void deleteChoicesByQuestionId(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new EntityNotFoundException("Question not found with id: " + questionId);
        }
        choiceRepository.deleteByQuestionId(questionId);
    }
}
