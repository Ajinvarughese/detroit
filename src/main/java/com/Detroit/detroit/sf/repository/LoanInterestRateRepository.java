package com.Detroit.detroit.sf.repository;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.sf.entity.LoanInterestRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanInterestRateRepository extends JpaRepository<LoanInterestRate, Long> {
    Optional<LoanInterestRate> findByLoanCategory(LoanCategory category);
}