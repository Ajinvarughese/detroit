package com.Detroit.detroit.questionnaire.entity;

import com.Detroit.detroit.enums.QuestionType;
import com.Detroit.detroit.library.EntityDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String questionUUID;
}
