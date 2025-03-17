package com.Detriot.detroit.service;

import com.Detriot.detroit.entity.Question;
import com.Detriot.detroit.entity.Questionnaire;
import com.Detriot.detroit.repository.QuestionRepository;
import com.Detriot.detroit.repository.QuestionnaireRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionnaireRepository questionnaireRepository;

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
    public Question addQuestion(Long questionnaireId, Question question) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> new IllegalArgumentException("No questionnaire found on id: "+questionnaireId));
        question.setQuestionnaire(questionnaire);
        return questionRepository.save(question);
    }

    //Update an existing question
    public Question updateQuestion(Long id, Question updatedQuestion) {
        return questionRepository.findById(id).map(question -> {
            question.setQuestionText(updatedQuestion.getQuestionText());
            question.setQuestionType(updatedQuestion.getQuestionType());
            return questionRepository.save(question);
        }).orElseThrow(() -> new IllegalArgumentException("Question not found with question id: "+id));
    }

    //Delete a question
    public void deleteQuestion(Long id) {
        if(!questionRepository.existsById(id)) {
            throw new IllegalArgumentException("Question not found with question id: "+id);
        }
        questionRepository.deleteById(id);
    }
}
