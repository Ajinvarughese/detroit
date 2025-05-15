package com.Detriot.detroit.questionnaire.entity;

import com.Detriot.detroit.enums.LoanCategory;
import com.Detriot.detroit.library.EntityDetails;
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

    private String title;

    private String description;

    private LoanCategory loanCategory;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

}
