package com.Detroit.detroit.questionnaire.entity;

import com.Detroit.detroit.library.EntityDetails;
import com.Detroit.detroit.sf.entity.User;
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
public class Answer extends EntityDetails {

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "choice_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Choice choice;

    private String answerText;
}

