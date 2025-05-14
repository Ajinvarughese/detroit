package com.Detriot.detroit.sf.service;


import com.Detriot.detroit.sf.entity.Transaction;
import com.Detriot.detroit.sf.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + id));
    }

    public List<Transaction> getTransactionsByLoanId(Long loanId) {
        return transactionRepository.findByLoanId(loanId);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new EntityNotFoundException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }
}
