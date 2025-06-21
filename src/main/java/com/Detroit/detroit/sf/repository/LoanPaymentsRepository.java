package com.Detroit.detroit.sf.repository;

import com.Detroit.detroit.sf.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanPaymentsRepository extends JpaRepository<LoanPayment, Long> {
    List<LoanPayment> findByLoanId(Long id);
}
