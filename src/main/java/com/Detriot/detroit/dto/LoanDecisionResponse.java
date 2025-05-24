package com.Detriot.detroit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanDecisionResponse {

    private boolean eligible;

    private int questionnaireScore;

    private BigDecimal emi;

    private String decisionReason;
}
