package com.Detroit.detroit.sf.controller;

import com.Detroit.detroit.dto.AnswerDTO;
import com.Detroit.detroit.dto.Login;
import com.Detroit.detroit.library.FileUpload;
import com.Detroit.detroit.sf.entity.Loan;
import com.Detroit.detroit.sf.entity.LoanPayment;
import com.Detroit.detroit.sf.service.LoanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/loan")
public class LoanController {

    private final LoanService loanService;
    private final FileUpload fileUpload;

    public LoanController(LoanService loanService, FileUpload fileUpload) {
        this.loanService = loanService;
        this.fileUpload = fileUpload;
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
    @GetMapping("/{loanUUID}")
    public ResponseEntity<Loan> getLoanById(@PathVariable String loanUUID) {
        return ResponseEntity.ok(loanService.getLoanById(UUID.fromString(loanUUID)));
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
    @PostMapping(path = "/file/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadProjectReport(@RequestParam("projectReport") MultipartFile file) throws IOException {
            String projectReportPath = fileUpload.uploadFile(file);
            return ResponseEntity.ok(projectReportPath);
    }

    @PutMapping
    public ResponseEntity<Loan> updateLoan(@RequestBody Loan loan) {
        return ResponseEntity.ok(loanService.updateLoan(loan));
    }

    @PutMapping("/newRequest")
    public ResponseEntity<Loan> newRequest(@RequestBody Loan loan) {
        return ResponseEntity.ok(loanService.newRequest(loan));
    }

    @PutMapping("/updateRequest")
    public ResponseEntity<Loan> updateRequest(@RequestBody Loan loan) {
        return ResponseEntity.ok(loanService.updateRequest(loan));
    }

    //  Delete a loan
    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId) {
        loanService.deleteLoan(loanId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/payment")
    public ResponseEntity<List<LoanPayment>> getAllPayment(@RequestBody Loan loan) {
        return ResponseEntity.ok(loanService.getLoanPayments(loan));
    }

    @PostMapping("/payment")
    public ResponseEntity<LoanPayment> addPayment(@RequestBody LoanPayment loanPayment) {
        return ResponseEntity.ok(loanService.addPayment(loanPayment));
    }
}
