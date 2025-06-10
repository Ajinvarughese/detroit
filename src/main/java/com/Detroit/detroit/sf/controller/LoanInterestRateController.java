package com.Detroit.detroit.sf.controller;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.sf.entity.LoanInterestRate;
import com.Detroit.detroit.sf.service.LoanInterestRateService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interest-rates")
public class LoanInterestRateController {

    private final LoanInterestRateService service;

    public LoanInterestRateController(LoanInterestRateService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LoanInterestRate>> getAllRates() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{category}")
    public ResponseEntity<LoanInterestRate> getRate(@PathVariable LoanCategory category) {
        return ResponseEntity.ok(service.getByCategory(category));
    }

    @PutMapping()
    public ResponseEntity<LoanInterestRate> updateRate(@RequestBody LoanInterestRate loanInterestRate) {
        return ResponseEntity.ok(service.updateRate(loanInterestRate));
    }
}
