package com.Detriot.detroit.rule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naceCodes;

    private String sector;

    private String activityName;

    private String contributionType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String substantialCriteria;

    @Column(columnDefinition = "TEXT")
    private String climateAdaptationDNSH;

    @Column(columnDefinition = "TEXT")
    private String waterDNSH;

    @Column(columnDefinition = "TEXT")
    private String circularEconomyDNSH;

    @Column(columnDefinition = "TEXT")
    private String pollutionPreventionDNSH;

    @Column(columnDefinition = "TEXT")
    private String biodiversityDNSH;
}
