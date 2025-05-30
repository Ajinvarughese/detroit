package com.Detroit.detroit.sf.controller;

import com.Detroit.detroit.dto.AnswerDTO;
import com.Detroit.detroit.dto.LoanApplication;
import com.Detroit.detroit.dto.Login;
import com.Detroit.detroit.sf.entity.Loan;
import com.Detroit.detroit.sf.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/loan")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
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

    @PostMapping("/application/{loanUUID}")
    public ResponseEntity<Loan> getLoanApplication(@RequestBody Login login, @PathVariable String loanUUID) {
        return ResponseEntity.ok(loanService.getLoanByUUID(login, UUID.fromString(loanUUID)));
    }


    @GetMapping("/categories")
    public ResponseEntity<List<String>> getLoanCategories() {
        return ResponseEntity.ok(loanService.getLoanCategories());
    }

    //  Get all loans by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getLoansByUserId(userId));
    }

    //  Create a loan manually (optional admin feature)
    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody AnswerDTO answerDTO) {
        return ResponseEntity.ok(loanService.createLoan(answerDTO));
    }


    // Full update to a loan
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Loan> updateLoan(@ModelAttribute LoanApplication loanApplication) {
        Loan updated = loanService.updateLoan(loanApplication.getLoan(), loanApplication.getProjectReport());
        return ResponseEntity.ok(updated);
    }

    //  Delete a loan
    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId) {
        loanService.deleteLoan(loanId);
        return ResponseEntity.noContent().build();
    }
}
