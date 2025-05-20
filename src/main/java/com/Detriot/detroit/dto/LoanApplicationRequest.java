package com.Detriot.detroit.dto;

import com.Detriot.detroit.enums.LoanCategory;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data

public class LoanApplicationRequest {

    private Long userId;

    private LoanCategory loanCategory;

    private BigDecimal amountRequested;

    private int durationInMonths;

    private List<String> questionnaireResponses;
}
