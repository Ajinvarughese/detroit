package com.Detroit.detroit.questionnaire.service;

import com.Detroit.detroit.dto.QuestionnaireDTO;
import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.enums.QuestionnaireType;
import com.Detroit.detroit.questionnaire.entity.Choice;
import com.Detroit.detroit.questionnaire.entity.Question;
import com.Detroit.detroit.questionnaire.entity.Questionnaire;
import com.Detroit.detroit.questionnaire.repository.ChoiceRepository;
import com.Detroit.detroit.questionnaire.repository.QuestionRepository;
import com.Detroit.detroit.questionnaire.repository.QuestionnaireRepository;
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
    public QuestionnaireDTO getCompleteQuestionnaire(String urlId) {

        Questionnaire questionnaire = questionnaireRepository.findByFormUrlId(urlId)
                .orElseThrow(() -> new EntityNotFoundException("Questionnaire not found with id: " + urlId));

        // Questionnaire DTO
        QuestionnaireDTO dto = new QuestionnaireDTO();
        dto.setId(questionnaire.getId());
        dto.setTitle(questionnaire.getTitle());
        dto.setDescription(questionnaire.getDescription());
        dto.setLoanCategory(questionnaire.getLoanCategory());
        dto.setQuestionnaireType(questionnaire.getQuestionnaireType());

        // Mapping questions
        List<Question> questions = questionRepository.findByQuestionnaireId(questionnaire.getId());
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

    // Get forms by loan category
    public List<Questionnaire> getQuestionnairesByLoanCategory(LoanCategory loanCategory){
        return questionnaireRepository.findByLoanCategory(loanCategory);
    }

    //Create a new Questionnaire
    public Questionnaire addQuestionnaire(Questionnaire questionnaire){
        return questionnaireRepository.save(questionnaire);
    }

    // Update questionnaire
    public Questionnaire updateQuestionnaire(Questionnaire updatedQuestionnaire) {
        return questionnaireRepository.findById(updatedQuestionnaire.getId())
                .map(existingQuestionnaire -> {
                    existingQuestionnaire.setTitle(updatedQuestionnaire.getTitle());
                    existingQuestionnaire.setDescription(updatedQuestionnaire.getDescription());
                    existingQuestionnaire.setLoanCategory(updatedQuestionnaire.getLoanCategory());

                    // If PRIMARY is being set
                    if (updatedQuestionnaire.getQuestionnaireType() == QuestionnaireType.PRIMARY) {
                        questionnaireRepository.setAllExceptToNormalInCategory(
                            updatedQuestionnaire.getId(),
                            updatedQuestionnaire.getLoanCategory(),
                            QuestionnaireType.NORMAL
                        );
                    }

                    existingQuestionnaire.setQuestionnaireType(updatedQuestionnaire.getQuestionnaireType());
                    return questionnaireRepository.save(existingQuestionnaire);
                })
                .orElseThrow(() -> new IllegalArgumentException("Questionnaire not found with id:" + updatedQuestionnaire.getId()));
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
