package com.Detroit.detroit.sf.entity;

import com.Detroit.detroit.library.EntityDetails;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class LoanPayment extends EntityDetails {

        @ManyToOne
        @JoinColumn(name = "loan_id", nullable = false)
        private Loan loan;

        @Column(nullable = false)
        private BigDecimal amountPaid;

        @Column(nullable = false)
        private LocalDateTime paymentDate;

        @Column(name = "is_on_time")
        private Boolean isOnTime;

        @Column(name = "remarks", length = 255)
        private String remarks;
    }

