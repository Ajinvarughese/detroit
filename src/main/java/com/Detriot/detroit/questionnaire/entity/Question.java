package com.Detriot.detroit.questionnaire.entity;

import com.Detriot.detroit.enums.QuestionType;
import com.Detriot.detroit.library.EntityDetails;
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
@NoArgsConstructor
@AllArgsConstructor
public class Question extends EntityDetails {

    @ManyToOne
    @JoinColumn(name = "questionnaire_id", nullable = false)
    private Questionnaire questionnaire;

    @Column(name="question_text", nullable = false)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(name="question_type", nullable = false)
    private QuestionType questionType;

}
