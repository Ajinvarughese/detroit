package com.Detriot.detroit.dto;


import com.Detriot.detroit.questionnaire.entity.Answer;
import com.Detriot.detroit.questionnaire.entity.Questionnaire;
import com.Detriot.detroit.sf.entity.User;
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
