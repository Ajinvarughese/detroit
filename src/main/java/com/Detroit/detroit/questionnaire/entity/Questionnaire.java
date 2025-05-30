package com.Detroit.detroit.questionnaire.entity;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.enums.QuestionnaireType;
import com.Detroit.detroit.library.EntityDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Questionnaire extends EntityDetails {

    private String formUrlId;

    private String title;

    private String description;

    private LoanCategory loanCategory;

    private QuestionnaireType questionnaireType;

}
