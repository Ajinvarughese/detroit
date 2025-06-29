package com.Detroit.detroit.sf.entity;

import com.Detroit.detroit.library.EntityDetails;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    private Double amountPaid;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime paymentDate;
}

