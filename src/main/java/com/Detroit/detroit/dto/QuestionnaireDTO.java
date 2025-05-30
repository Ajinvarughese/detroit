package com.Detroit.detroit.dto;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.enums.QuestionType;
import com.Detroit.detroit.enums.QuestionnaireType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionnaireDTO {
    private Long id;
    private String title;
    private String description;
    private LoanCategory loanCategory;
    private QuestionnaireType questionnaireType;
    private List<QuestionDTO> questions;

    @Data
    public static class QuestionDTO {
        private Long id;
        private String questionText;
        private QuestionType questionType;
        private String questionUUID;
        private List<ChoiceDTO> choices;
    }

    @Data
    public static class ChoiceDTO {
        private Long id;
        private String choiceText;
        private Long score;
    }
}

