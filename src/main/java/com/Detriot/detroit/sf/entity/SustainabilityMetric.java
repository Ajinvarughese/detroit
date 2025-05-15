package com.Detriot.detroit.sf.entity;

import com.Detriot.detroit.library.EntityDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sustainability_metrics")
public class SustainabilityMetric extends EntityDetails {


    private String metricName;

    private String category;

    private String unit;

    private String description;
}