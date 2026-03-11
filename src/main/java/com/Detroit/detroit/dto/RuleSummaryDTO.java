package com.Detroit.detroit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RuleSummaryDTO {

    private String sector;
    private String activity;
    private String contributionType;
    private String substantialCriteria;
}