package com.Detriot.detroit.service;

import com.Detriot.detroit.entity.Questionnaire;
import com.Detriot.detroit.repository.QuestionnaireRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;

    //Get all questions
    public List<Questionnaire> getAllQuestionnaire(){ return questionnaireRepository.findAll(); }


    //Get Specific questionnaire by id
    public Questionnaire getQuestionnaireById (Long id){
        return questionnaireRepository.findById(id)
                .orElseThrow(()-> new  EntityNotFoundException("Questionnaire not found with id: "+id));
    }

    //Create a new Questionnaire
    public Questionnaire addQuestionnaire(Questionnaire questionnaire){
        return questionnaireRepository.save(questionnaire);
    }

    //update an existing questionnaire
    public Questionnaire updateQuestionnaire(Long id,Questionnaire updatedQuestionnaire){
        return questionnaireRepository.findById(id).map( questionnaire -> {
            questionnaire.setTitle(updatedQuestionnaire.getTitle());
            questionnaire.setDescription(updatedQuestionnaire.getDescription());
            return questionnaireRepository.save(questionnaire);
        }).orElseThrow(() -> new IllegalArgumentException("Questionnaire not found with id:"+id));
    }
    //delete  a questionnaire by id

    public void deleteQuestionnaire(Long id){
        if (!questionnaireRepository.existsById(id)) {
            throw new IllegalArgumentException("Questionnaire not found with id:"+id);
        }
        questionnaireRepository.deleteById(id);
    }
}

 /*
        TODO:
              * Get All Questionnaires !
              * get Specific questionnaire by id
              * Create a new Questionnaire
              * update an existing questionnaire
              * delete  a questionnaire by id

    */
