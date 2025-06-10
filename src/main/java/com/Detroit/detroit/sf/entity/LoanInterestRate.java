package com.Detroit.detroit.sf.entity;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.library.EntityDetails;
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
@Table(name = "loan_category")
public class LoanInterestRate  extends EntityDetails {

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private LoanCategory loanCategory;

    @Column(nullable = false)
    private Double interestRate;
}
