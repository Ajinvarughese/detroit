package com.Detroit.detroit.sf.repository;

import com.Detroit.detroit.sf.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPaymentsRepository extends JpaRepository<LoanPayment, Long> {
}
