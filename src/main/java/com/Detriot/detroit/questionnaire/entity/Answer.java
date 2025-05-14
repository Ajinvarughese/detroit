package com.Detriot.detroit.questionnaire.entity;

import com.Detriot.detroit.entity.sf.User;
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
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User userId;
    //private Long questionnaireId;
    //private Long questionId;
    @Column(nullable = true)
    private Choice choice;
    private String answerText;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime submittedAt;



}
