package com.Detriot.detroit.sf.entity;


import com.Detriot.detroit.enums.TransactionType;
import com.Detriot.detroit.library.EntityDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends EntityDetails {


    @Column(nullable = false)
    private Long loanId; // Can be a @ManyToOne to Loan later

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

}
