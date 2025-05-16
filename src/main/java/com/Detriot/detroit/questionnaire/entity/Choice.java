package com.Detriot.detroit.questionnaire.entity;

import com.Detriot.detroit.library.EntityDetails;
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
public class Choice extends EntityDetails {
    @Column(name = "question_id", nullable = false)
    private Long questionId;
    @Column(name = "choice_text", nullable = false)
    private String choiceText;
}


