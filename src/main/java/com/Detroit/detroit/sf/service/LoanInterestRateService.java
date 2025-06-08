package com.Detroit.detroit.sf.service;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.sf.entity.LoanInterestRate;
import com.Detroit.detroit.sf.repository.LoanInterestRateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanInterestRateService {

    private final LoanInterestRateRepository repository;

    public List<LoanInterestRate> getAll() {
        return repository.findAll();
    }

    public LoanInterestRate getByCategory(LoanCategory category) {
        return repository.findByLoanCategory(category)
                .orElseThrow(() -> new EntityNotFoundException("Rate not set for category " + category));
    }

    public LoanInterestRate updateRate(LoanCategory category, double newRate) {
        LoanInterestRate rate = repository.findByLoanCategory(category)
                .orElse(new LoanInterestRate(category, newRate));
        rate.setInterestRate(newRate);
        return repository.save(rate);
    }
}
