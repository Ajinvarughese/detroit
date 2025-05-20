package com.Detriot.detroit.sf.repository;


import com.Detriot.detroit.sf.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId);

    @Query("SELECT l FROM Loan l LEFT JOIN FETCH l.payments WHERE l.id = :loanId")
    Optional<Loan> findByIdWithPayments(@Param("loanId") Long loanId);
}
