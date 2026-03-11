package com.Detroit.detroit.dto;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.enums.QuestionnaireType;
import com.Detroit.detroit.rule.entity.Rule;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireGenerationRequest {
    private String inputData;
    private LoanCategory loanCategory;
    private String rules;
}
