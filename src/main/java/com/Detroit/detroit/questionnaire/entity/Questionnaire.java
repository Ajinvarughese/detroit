package com.Detroit.detroit.questionnaire.entity;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.enums.QuestionnaireType;
import com.Detroit.detroit.library.EntityDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;
}
