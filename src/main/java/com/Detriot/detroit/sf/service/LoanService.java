package com.Detriot.detroit.sf.service;

import com.Detriot.detroit.dto.LoanApplicationRequest;
import com.Detriot.detroit.dto.LoanDecisionResponse;
import com.Detriot.detroit.dto.QuestionnaireResponseDto;
import com.Detriot.detroit.enums.LoanCategory;
import com.Detriot.detroit.enums.LoanStatus;
import com.Detriot.detroit.questionnaire.service.QuestionnaireService;
import com.Detriot.detroit.sf.entity.Loan;
import com.Detriot.detroit.sf.repository.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class LoanService {

    @Autowired
    private final LoanRepository loanRepository;

    @Autowired
    private final QuestionnaireService questionnaireService;

    // Process new loan application
    public LoanDecisionResponse processLoanApplication(LoanApplicationRequest request) {
        Loan loan = new Loan();
        loan.setUserId(request.getUserId());
        loan.setLoanCategory(request.getLoanCategory());
        loan.setAmount(request.getAmountRequested());
        loan.setDurationMonths(request.getDurationInMonths());

        // Evaluate questionnaire
        int score = questionnaireService.evaluateResponses(
                new QuestionnaireResponseDto(
                        request.getUserId().toString(),
                        request.getLoanCategory().name(),
                        request.getQuestionnaireResponses()
                )
        );
        loan.setQuestionnaireScore(score);

        boolean eligible = questionnaireService.isEligible(score, request.getLoanCategory().name());
        loan.setIsEligible(eligible);

        LoanDecisionResponse response = new LoanDecisionResponse();
        response.setQuestionnaireScore(score);
        response.setEligible(eligible);

        if (eligible) {
            BigDecimal annualInterest = new BigDecimal("0.08"); // 8% annual
            BigDecimal monthlyInterest = annualInterest.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);
            int N = request.getDurationInMonths();
            BigDecimal P = request.getAmountRequested();
            BigDecimal onePlusRPowerN = (BigDecimal.ONE.add(monthlyInterest)).pow(N);
            BigDecimal emi = P.multiply(monthlyInterest).multiply(onePlusRPowerN)
                    .divide(onePlusRPowerN.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

            loan.setInterestRate(annualInterest);
            loan.setLoanStartDate(LocalDateTime.now());
            loan.setStatus(LoanStatus.APPROVED);
            loan.setEligibilityReason("Approved based on eligibility score");

            response.setEmi(emi);
            response.setDecisionReason("Loan Approved");
        } else {
            loan.setStatus(LoanStatus.REJECTED);
            loan.setEligibilityReason("Questionnaire score too low");
            response.setDecisionReason("Loan Rejected due to low score");
        }

        loanRepository.save(loan);
        return response;
    }

    // Get full loan with payments
    public Loan getLoanWithPayments(Long loanId) {
        return loanRepository.findByIdWithPayments(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with ID: " + loanId));
    }

    // Get all loan categories
    public List<String> getLoanCategories() {
        return Arrays.stream(LoanCategory.values())
                                        .map(Enum::name)
                                        .collect(Collectors.toList());
    }

    // Get all loans
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    // Get loan by id
    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: " + loanId));
    }

    // Get loan by user id
    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    // Create a new loan
    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    // Update loan status
    public Loan updateLoanStatus(Long loanId, Loan updatedLoan) {
        Loan existing = getLoanById(loanId);
        existing.setStatus(updatedLoan.getStatus());
        return loanRepository.save(existing);
    }

    // Update loan manually (if needed)
    public Loan updateLoan(Long loanId, Loan updatedLoan) {
        Loan existing = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with ID: " + loanId));
        updatedLoan.setId(existing.getId());
        return loanRepository.save(updatedLoan);
    }

    // Delete existing loan
    public void deleteLoan(Long loanId) {
        if (!loanRepository.existsById(loanId)) {
            throw new EntityNotFoundException("Loan not found with id: " + loanId);
        }
        loanRepository.deleteById(loanId);
    }
}
