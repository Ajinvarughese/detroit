package com.Detriot.detroit.sf.service;


import com.Detriot.detroit.sf.entity.Loan;
import com.Detriot.detroit.sf.repository.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    // Get all loans
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    // Get loan by id
    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: " + loanId));
    }

    // Get loan by user id
    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    // Create new loan
    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    // Update loan status
    public Loan updateLoanStatus(Long loanId, Loan updatedLoan) {
        Loan existing = getLoanById(loanId);
        existing.setStatus(updatedLoan.getStatus());
        return loanRepository.save(existing);
    }

    // Delete existing loan
    public void deleteLoan(Long loanId) {
        if (!loanRepository.existsById(loanId)) {
            throw new EntityNotFoundException("Loan not found with id: " + loanId);
        }
        loanRepository.deleteById(loanId);
    }
}