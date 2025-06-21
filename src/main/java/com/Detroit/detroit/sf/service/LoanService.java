package com.Detroit.detroit.sf.service;

import com.Detroit.detroit.dto.AnswerDTO;
import com.Detroit.detroit.dto.Login;
import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.enums.LoanStatus;
import com.Detroit.detroit.questionnaire.entity.Questionnaire;
import com.Detroit.detroit.questionnaire.repository.QuestionnaireRepository;
import com.Detroit.detroit.questionnaire.service.AnswerService;
import com.Detroit.detroit.sf.entity.Loan;
import com.Detroit.detroit.sf.entity.LoanPayment;
import com.Detroit.detroit.sf.entity.User;
import com.Detroit.detroit.sf.repository.LoanPaymentsRepository;
import com.Detroit.detroit.sf.repository.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class LoanService {

    private final LoanRepository loanRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final AnswerService answerService;
    private PasswordEncoder passwordEncoder;
    private final LoanInterestRateService loanInterestRateService;
    private final LoanPaymentsRepository loanPaymentsRepository;

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
    public Loan getLoanById(UUID loanUUID) {
        return loanRepository.findByLoanUUID(loanUUID)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: " + loanUUID));
    }
    // Get loan by uuid
    public Loan getLoanByUUID(Login login, UUID uuid) {
        Loan loan = loanRepository.findByLoanUUID(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with uuid: " + uuid));
        if(!(loan.getStatus().equals(LoanStatus.CREATED))) {
            throw new IllegalArgumentException("Loan application is already created");
        } else if (!(loan.getUser().getEmail().equals(login.getEmail())) && !(loan.getUser().getPassword().equals(login.getPassword()))) {
            throw new IllegalArgumentException("Loan user ID must match existing user ID");
        }

        return loan;
    }

    // Get loan by user id
    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }


    public Loan createLoan(AnswerDTO answerDTO) throws NotAcceptableStatusException {
        Questionnaire questionnaire = questionnaireRepository.findById(answerDTO.getQuestionnaire().getId())
                .orElseThrow(() -> new EntityNotFoundException("No questionnaire found with id: "+answerDTO.getQuestionnaire().getId()));
        User user = answerDTO.getUser();
        Integer score = answerService.calculateScore(answerDTO);
        boolean isEligible = score >= 50;

        if(isEligible) {
            Loan newLoan = new Loan(
                    user,
                    questionnaire.getLoanCategory(),
                    null,
                    null,
                    null,
                    loanInterestRateService.getByCategory(questionnaire.getLoanCategory()).getInterestRate(),
                    LoanStatus.CREATED,
                    0.00,
                    questionnaire,
                    score,
                    null,
                    null,
                    null,
                    UUID.randomUUID(),
                    0.00,
                    0.00
            );
            answerService.saveMultipleAnswers(answerDTO);
            return loanRepository.save(newLoan);
        }else {
            throw new NotAcceptableStatusException("User not eligible");
        }
    }
    public Loan disbursing(Loan loan) {
        Loan existing = loanRepository.findById(loan.getId())
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: "+loan.getId()));
        Double currentDisbursed = existing.getDisbursedAmount() != null
            ? existing.getDisbursedAmount()
            : 0.0;
        currentDisbursed += loan.getDisburseAmount();
        existing.setDisburseAmount(loan.getDisburseAmount());
        existing.setDisbursedAmount(loan.getDisbursedAmount());
        if(existing.getDisbursedAmount() >= existing.getAmount()) {
            existing.setStatus(LoanStatus.DISBURSED);
        }

        return loanRepository.save(existing);
    }


    public Loan updateLoan(Loan updatedLoan) throws NotAcceptableStatusException, IllegalArgumentException {
        Loan existing = loanRepository.findById(updatedLoan.getId())
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: " + updatedLoan.getId()));

        if(!(existing.getUser().getId().equals(updatedLoan.getUser().getId())) && !(passwordEncoder.matches(updatedLoan.getUser().getPassword(), existing.getUser().getPassword()))) {
            throw new IllegalArgumentException("Loan user ID must match existing user ID");
        } else if (!(existing.getStatus().equals(LoanStatus.CREATED))) {
            throw new IllegalArgumentException("Loan application is already created");
        } else if(updatedLoan.getProjectReportPath() == null || updatedLoan.getProjectName() == null || updatedLoan.getAmount() == null || updatedLoan.getLoanCategory() == null || updatedLoan.getDurationMonths() == null) {
            throw new IllegalArgumentException("Loan amount, project report, loan category, project name and duration months must be provided");
        }
        updatedLoan.setAmountPending(updatedLoan.getAmount());
        updatedLoan.setAmountPerYear(calculateAmountPerYear(updatedLoan.getAmount(), updatedLoan.getInterestRate(), updatedLoan.getDurationMonths()));
        updatedLoan.setStatus(LoanStatus.REQUESTED);
        return loanRepository.save(updatedLoan);
    }


    public Loan newRequest(Loan loan) {
        Loan existing = loanRepository.findById(loan.getId())
                .orElseThrow(() -> new IllegalArgumentException("Loan not found with id: " + loan.getId()));
        existing.setAmount(loan.getAmount() != null ? loan.getAmount() : existing.getAmount());
        existing.setDurationMonths(loan.getDurationMonths() != null ? loan.getDurationMonths() : existing.getDurationMonths());
        existing.setAmountPending(loan.getAmount() != null ? loan.getAmount() : existing.getAmountPending());
        existing.setAmountPerYear(calculateAmountPerYear(loan.getAmount(), loan.getInterestRate(), loan.getDurationMonths()));
        existing.setStatus(LoanStatus.REQUESTED);
        return loanRepository.save(existing);
    }

    public Loan updateRequest(Loan loan) {
        Loan existing = loanRepository.findById(loan.getId())
                .orElseThrow(() -> new IllegalArgumentException("Loan not found with id: " + loan.getId()));
        if(loan.getStatus().equals(LoanStatus.REJECTED)) {
            existing.setStatus(LoanStatus.REJECTED);
            return loanRepository.save(existing);
        }
        if (existing.getAmount().compareTo(loan.getAmount()) != 0 || existing.getDurationMonths().compareTo(loan.getDurationMonths()) != 0) {
            existing.setAmount(loan.getAmount() != null ? loan.getAmount() : existing.getAmount());
            existing.setDurationMonths(loan.getDurationMonths() != null ? loan.getDurationMonths() : existing.getDurationMonths());
            existing.setAmountPending(loan.getAmount() != null ? loan.getAmount() : existing.getAmountPending());
            existing.setAmountPerYear(calculateAmountPerYear(loan.getAmount(), loan.getInterestRate(), loan.getDurationMonths()));
            existing.setStatus(LoanStatus.PENDING);
            return loanRepository.save(existing);
        }
        existing.setStatus(LoanStatus.APPROVED);
        return loanRepository.save(existing);
    }

    public List<LoanPayment> getLoanPayments(Loan loan) {
        Loan existing = loanRepository.findById(loan.getId())
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: "+loan.getId()));
        return loanPaymentsRepository.findByLoanId(existing.getId());

    }

    public LoanPayment addPayment(LoanPayment loanPayment) {
        Loan existing = loanRepository.findById(loanPayment.getLoan().getId())
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: "+loanPayment.getLoan().getId()));
        existing.setAmountPending(existing.getAmountPending() - loanPayment.getAmountPaid());
        if(existing.getAmountPending() <= 0) {
            existing.setStatus(LoanStatus.REPAID);
            loanRepository.save(existing);
        }
        return loanPaymentsRepository.save(loanPayment);
    }

    public Loan updateStatus(Loan loan) {
        Loan existing = loanRepository.findById(loan.getId())
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id+"+loan.getId()));
        existing.setStatus(loan.getStatus());
        return loanRepository.save(existing);
    }


    // Delete existing loan
    public void deleteLoan(Long loanId) {
        if (!loanRepository.existsById(loanId)) {
            throw new EntityNotFoundException("Loan not found with id: " + loanId);
        }
        loanRepository.deleteById(loanId);
    }

    public Double calculateAmountPerYear(Double amount, Double interest, Long durationMonths) {
        double oneMonth = amount/(durationMonths/12.0);
        System.out.println(oneMonth*(interest/100));
        return oneMonth*(interest/100);
    }
}
