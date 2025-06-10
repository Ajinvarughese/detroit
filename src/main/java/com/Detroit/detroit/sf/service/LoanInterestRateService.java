package com.Detroit.detroit.sf.service;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.sf.entity.LoanInterestRate;
import com.Detroit.detroit.sf.repository.LoanInterestRateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoanInterestRateService {

    private final LoanInterestRateRepository repository;

    public List<LoanInterestRate> getAll() {
        return repository.findAll();
    }

    public LoanInterestRate getByCategory(LoanCategory category) {
        return repository.findByLoanCategory(category)
                .orElseThrow(() -> new EntityNotFoundException("Rate not set for category " + category));
    }

    public LoanInterestRate updateRate(LoanInterestRate loanInterestRate) {
        LoanInterestRate rate = repository.findByLoanCategory(loanInterestRate.getLoanCategory())
                .orElse(new LoanInterestRate(loanInterestRate.getLoanCategory(), loanInterestRate.getInterestRate()));
        rate.setInterestRate(loanInterestRate.getInterestRate());
        return repository.save(rate);
    }
}
