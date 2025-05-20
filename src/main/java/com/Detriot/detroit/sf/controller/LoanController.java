package com.Detriot.detroit.sf.controller;

import com.Detriot.detroit.dto.LoanApplicationRequest;
import com.Detriot.detroit.dto.LoanDecisionResponse;
import com.Detriot.detroit.sf.entity.Loan;
import com.Detriot.detroit.sf.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    //  Submit a loan application
    @PostMapping("/apply")
    public ResponseEntity<LoanDecisionResponse> applyForLoan(@RequestBody LoanApplicationRequest request) {
        LoanDecisionResponse response = loanService.processLoanApplication(request);
        return ResponseEntity.ok(response);
    }

    //  Get loan with payments
    @GetMapping("/with-payments/{loanId}")
    public ResponseEntity<Loan> getLoanWithPayments(@PathVariable Long loanId) {
        Loan loan = loanService.getLoanWithPayments(loanId);
        return ResponseEntity.ok(loan);
    }

    // Get all loans
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    //  Get loan by ID
    @GetMapping("/id/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.getLoanById(loanId));
    }

    //  Get all loans by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getLoansByUserId(userId));
    }

    //  Create a loan manually (optional admin feature)
    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        return ResponseEntity.ok(loanService.createLoan(loan));
    }

    //  Update loan status (patch)
    @PatchMapping("/{loanId}")
    public ResponseEntity<Loan> updateLoanStatus(@PathVariable Long loanId, @RequestBody Loan loan) {
        return ResponseEntity.ok(loanService.updateLoanStatus(loanId, loan));
    }

    // Full update to a loan (optional admin feature)
    @PutMapping("/{loanId}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long loanId, @RequestBody Loan updatedLoan) {
        Loan updated = loanService.updateLoan(loanId, updatedLoan);
        return ResponseEntity.ok(updated);
    }

    //  Delete a loan
    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId) {
        loanService.deleteLoan(loanId);
        return ResponseEntity.noContent().build();
    }
}
