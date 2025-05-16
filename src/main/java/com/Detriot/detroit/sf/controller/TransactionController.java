package com.Detriot.detroit.sf.controller;

import com.Detriot.detroit.sf.entity.LoanTransaction;
import com.Detriot.detroit.sf.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<LoanTransaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanTransaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<LoanTransaction>> getTransactionsByLoanId(@PathVariable Long loanId) {
        return ResponseEntity.ok(transactionService.getTransactionsByLoanId(loanId));
    }

    @PostMapping
    public ResponseEntity<LoanTransaction> createTransaction(@RequestBody LoanTransaction transaction) {
        return ResponseEntity.ok(transactionService.createTransaction(transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
