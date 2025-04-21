package com.Detriot.detroit.service;

import com.Detriot.detroit.entity.Choice;
import com.Detriot.detroit.repository.ChoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChoiceService {
    private final ChoiceRepository choiceRepository;
    //Get all choices
    public List<Choice> getAllChoices(){

        return choiceRepository.findAll();
    }
    //Get choice by id
    public Choice getChoiceById(Long id){
        return choiceRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Choice not found with id:"+id));
    }
    public List<Choice> getChoicesByQuestionId(Long questionId){
        return choiceRepository.findByQuestionId(questionId);
    }
    //Save choice
    public Choice saveChoice(Choice choice){
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
