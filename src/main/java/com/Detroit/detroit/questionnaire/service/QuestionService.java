package com.Detroit.detroit.questionnaire.service;

import com.Detroit.detroit.questionnaire.entity.Question;
import com.Detroit.detroit.questionnaire.entity.Questionnaire;
import com.Detroit.detroit.questionnaire.repository.ChoiceRepository;
import com.Detroit.detroit.questionnaire.repository.QuestionRepository;
import com.Detroit.detroit.questionnaire.repository.QuestionnaireRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final ChoiceRepository choiceRepository;

    //Get all questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    //Get Single question by ID
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No question found by id: "+id));
    }

    //Get question by questionnaire
    public List<Question> getQuestionsByQuestionnaireId(Long questionnaireId) {
        return questionRepository.findByQuestionnaireId(questionnaireId);
    }

    //Add a new question to a questionnaire
    public Question addQuestion(Question question) {

        if (question.getQuestionnaire() == null || question.getQuestionnaire().getId() == null) {
            throw new IllegalArgumentException("Questionnaire ID must be provided");
        }

        Questionnaire questionnaire = questionnaireRepository.findById(question.getQuestionnaire().getId())
            .orElseThrow(() -> new IllegalArgumentException("No questionnaire found on id: " + question.getQuestionnaire().getId()));

        question.setQuestionnaire(questionnaire);

        return questionRepository.save(question);
    }


    //Update an existing question
    public Question updateQuestion(Question updatedQuestion) {
        return questionRepository.findById(updatedQuestion.getId()).map(question -> {
            question.setQuestionText(updatedQuestion.getQuestionText());
            question.setQuestionType(updatedQuestion.getQuestionType());
            return questionRepository.save(question);
        }).orElseThrow(() -> new IllegalArgumentException("Question not found with question id: "+updatedQuestion.getId()));
    }

    //Delete a question
    @Transactional
    public void deleteQuestion(Long id) {
        if(!questionRepository.existsById(id)) {
            throw new IllegalArgumentException("Question not found with question id: "+id);
        }
        choiceRepository.deleteByQuestionId(id);
        questionRepository.deleteById(id);
    }

    @Transactional
    public void deleteQuestionsByQuestionnaireId(Long questionnaireId) {
        if(!questionnaireRepository.existsById(questionnaireId)) {
            throw new IllegalArgumentException("Questionnaire not found with questionnaire id: "+questionnaireId);
        }
        List<Question> questions = questionRepository.findByQuestionnaireId(questionnaireId);
        for (Question question : questions) {
            choiceRepository.deleteByQuestionId(question.getId());
        }
        questionRepository.deleteByQuestionnaireId(questionnaireId);
    }
}
