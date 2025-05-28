package com.Detriot.detroit.questionnaire.service;

import com.Detriot.detroit.dto.AnswerDTO;
import com.Detriot.detroit.enums.QuestionType;
import com.Detriot.detroit.questionnaire.entity.Answer;
import com.Detriot.detroit.questionnaire.entity.Choice;
import com.Detriot.detroit.questionnaire.entity.Question;
import com.Detriot.detroit.questionnaire.entity.Questionnaire;
import com.Detriot.detroit.questionnaire.repository.AnswerRepository;
import com.Detriot.detroit.questionnaire.repository.ChoiceRepository;
import com.Detriot.detroit.questionnaire.repository.QuestionRepository;
import com.Detriot.detroit.questionnaire.repository.QuestionnaireRepository;
import com.Detriot.detroit.sf.entity.User;
import com.Detriot.detroit.sf.repository.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;
    private final LoanRepository loanRepository;
    private final QuestionnaireRepository questionnaireRepository;

    public List<Answer>getAllAnswers(){
        return answerRepository.findAll();
    }
    public Answer getAnswerById(Long id){
        return answerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Answer not found with id:"+id));
    }
    public List<Answer>getAnswerByUserId(Long userId){
        return answerRepository.findByUserId(userId);
    }
    public Answer saveAnswer(Answer answer){
        return answerRepository.save(answer);
    }

    public List<Answer> saveMultipleAnswers(AnswerDTO answers) {
        Questionnaire questionnaire = answers.getQuestionnaire();
        List<Answer> userAnswers = answers.getAnswers();
        return answerRepository.saveAll(userAnswers);
    }

    public void deleteAnswer(Long id){
        if (!answerRepository.existsById(id)){
            throw new EntityNotFoundException("Answer not found with id:"+id);
        }
        answerRepository.deleteById(id);
    }


    public Integer calculateScore(AnswerDTO answersResponse) {
        Questionnaire questionnaire = answersResponse.getQuestionnaire();
        List<Answer> userAnswers = answersResponse.getAnswers();
        User user = answersResponse.getUser();
        double totalScore = 0;
        double maxPossibleScore = 0;

        // Group choices by question for easy lookup
        Map<Long, List<Choice>> choicesByQuestion = getChoicesByQuestion(questionnaire);

        // --- Extract choice IDs from answers ---
        Set<Long> choiceIds = userAnswers.stream()
                .filter(a -> a.getChoice() != null)
                .map(a -> a.getChoice().getId())
                .collect(Collectors.toSet());

        // --- Fetch full choices from DB ---
        List<Choice> choicesFromDB = choiceRepository.findAllById(choiceIds);

        // --- Map choiceId -> full Choice ---
        Map<Long, Choice> choiceMap = choicesFromDB.stream()
                .collect(Collectors.toMap(Choice::getId, c -> c));

        // --- Replace each Answer's choice with the full Choice from DB ---
        userAnswers.forEach(a -> {
            if (a.getChoice() != null) {
                a.setChoice(choiceMap.get(a.getChoice().getId()));
            }
        });

        // --- Now build userAnswersByQuestion ---
        Map<Long, List<Answer>> userAnswersByQuestion = userAnswers.stream()
                .filter(a -> a.getChoice() != null)
                .collect(Collectors.groupingBy(a -> a.getChoice().getQuestion().getId()));

        for (Question question : getQuestions(questionnaire)) {
            List<Choice> choices = choicesByQuestion.getOrDefault(question.getId(), new ArrayList<>());

            // Calculate max possible score per question
            double questionMaxScore = 0;
            if (question.getQuestionType() == QuestionType.RADIO || question.getQuestionType() == QuestionType.DROPDOWN) {
                questionMaxScore = choices.stream()
                    .filter(c -> c.getScore() != null && c.getScore() > 0)
                    .mapToLong(Choice::getScore)
                    .max()
                    .orElse(0);
            } else if (question.getQuestionType() == QuestionType.CHECKBOX) {
                questionMaxScore = choices.stream()
                    .filter(c -> c.getScore() != null && c.getScore() > 0)
                    .mapToLong(Choice::getScore)
                    .sum();
            }
            maxPossibleScore += questionMaxScore;

            // Calculate user’s score for this question
            List<Answer> answers = userAnswersByQuestion.getOrDefault(question.getId(), new ArrayList<>());
            if (!answers.isEmpty()) {
                double userQuestionScore = answers.stream()
                    .filter(a -> a.getChoice() != null && a.getChoice().getScore() != null)
                    .mapToLong(a -> a.getChoice().getScore())
                    .sum();
                totalScore += userQuestionScore;
            }
        }

        double percentageScore = (maxPossibleScore > 0) ? (totalScore / maxPossibleScore) * 100 : 0;
        return (int) Math.round(percentageScore);
    }




    private Map<Long, List<Choice>> getChoicesByQuestion(Questionnaire questionnaire) {
        Map<Long, List<Choice>> map = new HashMap<>();
        for (Question question : getQuestions(questionnaire)) {
            map.put(question.getId(), getChoices(question));
        }
        return map;
    }

    private List<Question> getQuestions(Questionnaire questionnaire) {
        return questionRepository.findByQuestionnaireId(questionnaire.getId());
    }

    private List<Choice> getChoices(Question question) {
        return choiceRepository.findByQuestionId(question.getId());
    }
}
