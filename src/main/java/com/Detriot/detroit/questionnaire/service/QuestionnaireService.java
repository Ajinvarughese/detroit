package com.Detriot.detroit.questionnaire.service;

import com.Detriot.detroit.dto.QuestionnaireDTO;
import com.Detriot.detroit.dto.QuestionnaireResponseDto;
import com.Detriot.detroit.questionnaire.entity.Choice;
import com.Detriot.detroit.questionnaire.entity.Question;
import com.Detriot.detroit.questionnaire.entity.Questionnaire;
import com.Detriot.detroit.questionnaire.repository.ChoiceRepository;
import com.Detriot.detroit.questionnaire.repository.QuestionRepository;
import com.Detriot.detroit.questionnaire.repository.QuestionnaireRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    //Get all questions
    public List<Questionnaire> getAllQuestionnaire(){ return questionnaireRepository.findAll(); }


    //Get Specific questionnaire by id
    public Questionnaire getQuestionnaireById (Long id){
        return questionnaireRepository.findById(id)
                .orElseThrow(()-> new  EntityNotFoundException("Questionnaire not found with id: "+id));
    }

    // Get the complete form
    public QuestionnaireDTO getCompleteQuestionnaire(Long id) {

        Questionnaire questionnaire = questionnaireRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Questionnaire not found with id: " + id));

        // Questionnaire DTO
        QuestionnaireDTO dto = new QuestionnaireDTO();
        dto.setId(questionnaire.getId());
        dto.setTitle(questionnaire.getTitle());
        dto.setDescription(questionnaire.getDescription());
        dto.setLoanCategory(questionnaire.getLoanCategory());

        // Mapping questions
        List<Question> questions = questionRepository.findByQuestionnaireId(id);
        List<QuestionnaireDTO.QuestionDTO> questionDTOs = new ArrayList<>();

        for (Question question : questions) {
            QuestionnaireDTO.QuestionDTO questionDTO = new QuestionnaireDTO.QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setQuestionText(question.getQuestionText());
            questionDTO.setQuestionUUID(question.getQuestionUUID());
            questionDTO.setQuestionType(question.getQuestionType());

            // Mapping choices
            List<Choice> choices = choiceRepository.findByQuestionId(question.getId());
            List<QuestionnaireDTO.ChoiceDTO> allChoices = new ArrayList<>();
            for (Choice choice : choices) {
                QuestionnaireDTO.ChoiceDTO choiceDTO = new QuestionnaireDTO.ChoiceDTO();
                choiceDTO.setId(choice.getId());
                choiceDTO.setChoiceText(choice.getChoiceText());
                choiceDTO.setScore(choice.getScore());
                allChoices.add(choiceDTO);
            }

            questionDTO.setChoices(allChoices);
            questionDTOs.add(questionDTO);
        }
        dto.setQuestions(questionDTOs);
        return dto;
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

    //remove questionnaire
    @Transactional
    public void deleteQuestionnaire(Long id) {
        Questionnaire questionnaire = questionnaireRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Questionnaire not found with id: " + id));

        List<Question> questions = questionRepository.findByQuestionnaireId(id);
        for (Question question : questions) {
            choiceRepository.deleteByQuestionId(question.getId());
        }
        questionRepository.deleteByQuestionnaireId(id);
        questionnaireRepository.deleteById(id);

    }
}
