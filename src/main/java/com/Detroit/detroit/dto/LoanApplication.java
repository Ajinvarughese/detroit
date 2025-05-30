package com.Detroit.detroit.dto;

import com.Detroit.detroit.sf.entity.Loan;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class LoanApplication {
    private Loan loan;
    private MultipartFile projectReport;
}
