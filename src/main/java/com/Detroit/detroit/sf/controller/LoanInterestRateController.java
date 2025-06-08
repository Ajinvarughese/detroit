package com.Detroit.detroit.sf.controller;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.sf.entity.LoanInterestRate;
import com.Detroit.detroit.sf.service.LoanInterestRateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interest-rates")
@AllArgsConstructor

public class LoanInterestRateController {

    private final LoanInterestRateService service;

    @GetMapping
    public List<LoanInterestRate> getAllRates() {
        return service.getAll();
    }

    @GetMapping("/{category}")
    public LoanInterestRate getRate(@PathVariable LoanCategory category) {
        return service.getByCategory(category);
    }

    @PutMapping("/{category}")
    public LoanInterestRate updateRate(@PathVariable LoanCategory category,
                                       @RequestParam double newRate) {
        return service.updateRate(category, newRate);
    }
}
