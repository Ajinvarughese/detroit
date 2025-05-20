package com.Detriot.detroit.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class QuestionnaireResponseDto {

    private String userId;
    private String loanCategory;
    private List<String> responses;
}
