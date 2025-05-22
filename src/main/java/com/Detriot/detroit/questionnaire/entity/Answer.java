package com.Detriot.detroit.questionnaire.entity;

import com.Detriot.detroit.library.EntityDetails;
import com.Detriot.detroit.sf.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Answer extends EntityDetails {

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "choice_id", nullable = true)
    private Choice choice;

    private String answerText;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime submittedAt;
}

