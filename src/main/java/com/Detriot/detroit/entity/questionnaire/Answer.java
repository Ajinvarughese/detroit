package com.Detriot.detroit.entity.questionnaire;

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
    private Long userId;
    private Long questionnaireId;
    private Long questionId;
    @Column(nullable = true)
    private Long choiceId;
    private String answerText;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime submittedAt;



}
