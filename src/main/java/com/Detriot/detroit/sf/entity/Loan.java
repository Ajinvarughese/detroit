package com.Detriot.detroit.sf.entity;

import com.Detriot.detroit.enums.LoanCategory;
import com.Detriot.detroit.enums.LoanStatus;
import com.Detriot.detroit.library.EntityDetails;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loan extends EntityDetails {

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanCategory loanCategory;

    @Column(nullable = false)
    private BigDecimal amount;

    // ex: 12months - where the applicant finishes loan repayment within 12 months
    @Column(name = "duration_months")
    private Integer durationMonths;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @Column(name = "is_eligible")
    private Boolean isEligible;

    @Column(name = "eligibility_reason", length = 500)
    private String eligibilityReason;

    @Column(name = "amount_pending")
    private BigDecimal amount_Pending;

    @Column(name = "questionnaire_score")
    private Integer questionnaireScore;

    @Column(name = "document_verified")
    private Boolean documentVerified = false;

    @Column(name = "project_report_path")
    private String projectReportPath;

    @Column(name = "loan_start_date")
    private LocalDateTime loanStartDate;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanPayment> payments;
}
