package com.Detriot.detroit.sf.repository;


import com.Detriot.detroit.sf.entity.LoanTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, Long> {
    List<LoanTransaction> findByLoanId(Long loanId);
}
