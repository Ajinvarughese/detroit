package com.Detriot.detroit.questionnaire.service;

import com.Detriot.detroit.questionnaire.entity.Questionnaire;
import com.Detriot.detroit.questionnaire.repository.QuestionnaireRepository;
import jakarta.persistence.EntityNotFoundException;
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

    // Get questionnaire by form url id
    public Questionnaire getQuestionnaireByFormUrlId(String formUrlId){
        return questionnaireRepository.findByFormUrlId(formUrlId)
                .orElseThrow(()-> new  EntityNotFoundException("Questionnaire not found with form url id: "+formUrlId));
    }

    //Create a new Questionnaire
    public Questionnaire addQuestionnaire(Questionnaire questionnaire){
        return questionnaireRepository.save(questionnaire);
    }

    //update an existing questionnaire
    public Questionnaire updateQuestionnaire(Questionnaire updatedQuestionnaire){
        return questionnaireRepository.findById(updatedQuestionnaire.getId()).map( questionnaire -> {
            questionnaire.setTitle(updatedQuestionnaire.getTitle());
            questionnaire.setDescription(updatedQuestionnaire.getDescription());
            return questionnaireRepository.save(questionnaire);
        }).orElseThrow(() -> new IllegalArgumentException("Questionnaire not found with id:"+updatedQuestionnaire.getId()));
    }
    //delete  a questionnaire by id

    public void deleteQuestionnaire(Long id){
        if (!questionnaireRepository.existsById(id)) {
            throw new IllegalArgumentException("Questionnaire not found with id:"+id);
        }
        questionnaireRepository.deleteById(id);
    }
}


