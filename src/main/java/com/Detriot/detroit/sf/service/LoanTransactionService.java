package com.Detriot.detroit.sf.service;

<<<<<<< Updated upstream:src/main/java/com/Detriot/detroit/sf/service/TransactionService.java
=======
import com.Detriot.detroit.enums.TransactionType;
import com.Detriot.detroit.sf.entity.Loan;
>>>>>>> Stashed changes:src/main/java/com/Detriot/detroit/sf/service/LoanTransactionService.java
import com.Detriot.detroit.sf.entity.LoanTransaction;
import com.Detriot.detroit.sf.repository.LoanRepository;
import com.Detriot.detroit.sf.repository.LoanTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanTransactionService {

    private final LoanTransactionRepository transactionRepository;
    private final LoanRepository loanRepository;

    public List<LoanTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public LoanTransaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + id));
    }

    public List<LoanTransaction> getTransactionsByLoanId(Long loanId) {
        return transactionRepository.findByLoanId(loanId);
    }

    public LoanTransaction createTransaction(LoanTransaction transaction) {
        Long loanId = transaction.getLoan().getId();
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: " + loanId));

        // Ensure transaction has timestamp
        transaction.setTransactionDate(LocalDateTime.now());

        // Apply transaction effect
        if (transaction.getTransactionType() == TransactionType.PAYMENT) {
            BigDecimal updatedPending = loan.getAmount_Pending().subtract(transaction.getAmount());
            loan.setAmount_Pending(updatedPending.max(BigDecimal.ZERO));
            loanRepository.save(loan);
        }

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new EntityNotFoundException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }
}
