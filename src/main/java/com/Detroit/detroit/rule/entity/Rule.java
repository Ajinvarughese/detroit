package com.Detroit.detroit.rule.entity;

import com.Detroit.detroit.enums.LoanCategory;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LoanCategory ruleCategory;


    private String nace;

    private String sector;

    @Column(name = "activity_number")
    private String activityNumber;

    @Column(name = "activity")
    private String activityName;

    @Column(name = "contribution_type", columnDefinition = "TEXT")
    private String contributionType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "substantial_contribution_criteria", columnDefinition = "TEXT")
    private String substantialCriteria;

    @Column(name = "climate_mitigation_dnsh", columnDefinition = "TEXT")
    private String climateMitigation;

    @Column(name = "circular_economy_dnsh", columnDefinition = "TEXT")
    private String circularEconomyDNSH;

    @Column(name = "climate_adaptation_dnsh", columnDefinition = "TEXT")
    private String climateAdaptationDNSH;

    @Column(name = "water_dnsh", columnDefinition = "TEXT")
    private String waterDNSH;

    @Column(name = "pollution_prevention_dnsh", columnDefinition = "TEXT")
    private String pollutionPreventionDNSH;

    @Column(name = "biodiversity_dnsh", columnDefinition = "TEXT")
    private String biodiversityDNSH;

    @Column(name = "footnotes", columnDefinition = "TEXT")
    private String footNotes;
}
