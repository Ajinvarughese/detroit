package com.Detroit.detroit.sf.entity;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.enums.LoanStatus;
import com.Detroit.detroit.library.EntityDetails;
import com.Detroit.detroit.questionnaire.entity.Questionnaire;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Loan extends EntityDetails {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanCategory loanCategory;

    private Double amount;

    private Double amountPerYear;

    @Column(name = "duration_months")
    private Long durationMonths;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @Column(name = "amount_pending")
    private Double amountPending;

    @ManyToOne
    @JoinColumn(name = "questionnaire_id", nullable = true)
    private Questionnaire questionnaire;

    @Column(name = "questionnaire_score")
    private Integer questionnaireScore;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_report_path")
    private String projectReportPath;

    @Column(name = "loan_start_date")
    private LocalDateTime loanStartDate;

    @Column(name = "loan_url")
    private UUID loanUUID;

    private Double disburseAmount;
    private Double disbursedAmount;

}
