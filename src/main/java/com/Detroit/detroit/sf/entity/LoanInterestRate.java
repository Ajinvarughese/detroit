package com.Detroit.detroit.sf.entity;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.library.EntityDetails;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class LoanInterestRate  extends EntityDetails {

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private LoanCategory loanCategory;

    @Column(nullable = false)
    private Double interestRate;

    public LoanInterestRate() {}

    public LoanInterestRate(LoanCategory loanCategory, Double interestRate) {
        this.loanCategory = loanCategory;
        this.interestRate = interestRate;
    }

    public LoanCategory getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(LoanCategory loanCategory) {
        this.loanCategory = loanCategory;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}
