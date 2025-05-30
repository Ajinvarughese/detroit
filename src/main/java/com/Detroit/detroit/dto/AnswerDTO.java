package com.Detroit.detroit.dto;


import com.Detroit.detroit.questionnaire.entity.Answer;
import com.Detroit.detroit.questionnaire.entity.Questionnaire;
import com.Detroit.detroit.sf.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AnswerDTO {
    private User user;
    private Questionnaire questionnaire;
    private List<Answer> answers;
}
