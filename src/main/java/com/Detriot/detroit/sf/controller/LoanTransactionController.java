package com.Detriot.detroit.sf.controller;

import com.Detriot.detroit.sf.entity.LoanTransaction;
import com.Detriot.detroit.sf.service.LoanTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class LoanTransactionController {

    private final LoanTransactionService transactionService;

    @Autowired
    public LoanTransactionController(LoanTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //get all transactions
    @GetMapping
    public ResponseEntity<List<LoanTransaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    //get transaction by id
    @GetMapping("/{id}")
    public ResponseEntity<LoanTransaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    //get transactions by loan id
    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<LoanTransaction>> getTransactionsByLoanId(@PathVariable Long loanId) {
        return ResponseEntity.ok(transactionService.getTransactionsByLoanId(loanId));
    }

    // to create a transactions
    @PostMapping
    public ResponseEntity<LoanTransaction> createTransaction(@RequestBody LoanTransaction transaction) {
        return ResponseEntity.ok(transactionService.createTransaction(transaction));
    }
    // to delete a transactions
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
