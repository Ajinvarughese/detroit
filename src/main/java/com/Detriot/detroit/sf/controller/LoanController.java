package com.Detriot.detroit.sf.controller;


import com.Detriot.detroit.sf.entity.Loan;
import com.Detriot.detroit.sf.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.getLoanById(loanId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getLoansByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        return ResponseEntity.ok(loanService.createLoan(loan));
    }

    @PatchMapping("/{loanId}")
    public ResponseEntity<Loan> updateLoanStatus(@PathVariable Long loanId, @RequestBody Loan loan) {
        return ResponseEntity.ok(loanService.updateLoanStatus(loanId, loan));
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId) {
        loanService.deleteLoan(loanId);
        return ResponseEntity.noContent().build();
    }
}
