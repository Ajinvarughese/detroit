package com.Detriot.detroit.questionnaire.service;

import com.Detriot.detroit.dto.QuestionnaireResponseDto;
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
    public Questionnaire updateQuestionnaire(Questionnaire updatedQuestionnaire) {
        return questionnaireRepository.findById(updatedQuestionnaire.getId()).map(questionnaire -> {
            questionnaire.setTitle(updatedQuestionnaire.getTitle());
            questionnaire.setDescription(updatedQuestionnaire.getDescription());
            questionnaire.setLoanCategory(updatedQuestionnaire.getLoanCategory());
            return questionnaireRepository.save(questionnaire);
        }).orElseThrow(() -> new IllegalArgumentException("Questionnaire not found with id:" + updatedQuestionnaire.getId()));
    }

    // Delete a questionnaire by ID
    public void deleteQuestionnaire(Long id) {
        if (!questionnaireRepository.existsById(id)) {
            throw new IllegalArgumentException("Questionnaire not found with id: " + id);
        }
        questionnaireRepository.deleteById(id);
    }

    // Evaluate questionnaire responses and return score
    public int evaluateResponses(QuestionnaireResponseDto dto) {
        List<String> responses = dto.getResponses();
        int score = 0;

        for (String answer : responses) {
            // Simple scoring logic: "yes" answers get 10 points
            if (answer != null && answer.trim().equalsIgnoreCase("yes")) {
                score += 10;
            }
        }

        return score;
    }

    // Determine if user is eligible based on score and loan category
    public boolean isEligible(int score, String category) {
        // You can apply different thresholds per category if needed
        return score >= 30; // Example threshold
    }
}
