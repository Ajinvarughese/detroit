package com.Detriot.detroit.dto;

import com.Detriot.detroit.enums.LoanCategory;
import com.Detriot.detroit.enums.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionnaireDTO {
    private Long id;
    private String title;
    private String description;
    private LoanCategory loanCategory;
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

