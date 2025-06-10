package com.Detroit.detroit.sf.service;

import com.Detroit.detroit.dto.AnswerDTO;
import com.Detroit.detroit.dto.LoanApplication;
import com.Detroit.detroit.dto.Login;
import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.enums.LoanStatus;
import com.Detroit.detroit.enums.Role;
import com.Detroit.detroit.library.FileUpload;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.NotAcceptableStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                    loanInterestRateService.getByCategory(questionnaire.getLoanCategory()).getInterestRate(),
                    LoanStatus.CREATED,
                    null,
                    questionnaire,
                    score,
                    null,
                    null,
                    null,
                    null,
                    UUID.randomUUID()
            );
            answerService.saveMultipleAnswers(answerDTO);
            return loanRepository.save(newLoan);
        }else {
            throw new NotAcceptableStatusException("User not eligible");
        }
    }

    // Update loan manually (if needed)
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
        updatedLoan.setStatus(LoanStatus.REQUESTED);
        return loanRepository.save(updatedLoan);
    }


    public Loan newRequest(Loan loan) {
        Loan existing = loanRepository.findById(loan.getId())
                .orElseThrow(() -> new IllegalArgumentException("Loan not found with id: " + loan.getId()));
        existing.setAmount(loan.getAmount() != null ? loan.getAmount() : existing.getAmount());
        existing.setInterestRate(loan.getInterestRate() != null ? loan.getInterestRate() : existing.getInterestRate());
        existing.setDurationMonths(loan.getDurationMonths() != null ? loan.getDurationMonths() : existing.getDurationMonths());
        existing.setAmountPending(loan.getAmount() != null ? loan.getAmount() : existing.getAmountPending());
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
        if(loan.getStatus().equals(LoanStatus.DISBURSED)) {
            existing.setStatus(LoanStatus.DISBURSED);
            return loanRepository.save(existing);
        }
        if (existing.getAmount().compareTo(loan.getAmount()) != 0 || existing.getDurationMonths().compareTo(loan.getDurationMonths()) != 0) {
            existing.setAmount(loan.getAmount() != null ? loan.getAmount() : existing.getAmount());
            existing.setDurationMonths(loan.getDurationMonths() != null ? loan.getDurationMonths() : existing.getDurationMonths());
            existing.setAmountPending(loan.getAmount() != null ? loan.getAmount() : existing.getAmountPending());
            existing.setStatus(LoanStatus.PENDING);
            return loanRepository.save(existing);
        }
        existing.setStatus(LoanStatus.APPROVED);
        return loanRepository.save(existing);
    }

    public List<LoanPayment> getLoanPayments(Loan loan) {
        Loan existing = loanRepository.findById(loan.getId())
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: "+ loan.getId()));
        return existing.getPayments();
    }
    public LoanPayment addPayment(LoanPayment loanPayment) {
        Loan existing = loanRepository.findById(loanPayment.getLoan().getId())
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: "+loanPayment.getLoan().getId()));
        return loanPaymentsRepository.save(loanPayment);
    }


    // Delete existing loan
    public void deleteLoan(Long loanId) {
        if (!loanRepository.existsById(loanId)) {
            throw new EntityNotFoundException("Loan not found with id: " + loanId);
        }
        loanRepository.deleteById(loanId);
    }
}
