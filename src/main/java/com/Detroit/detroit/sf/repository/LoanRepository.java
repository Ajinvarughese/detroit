package com.Detroit.detroit.sf.repository;


import com.Detroit.detroit.sf.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId);
    Optional<Loan> findByLoanUUID(UUID uuid);
}
