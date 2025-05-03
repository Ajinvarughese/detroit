package com.Detriot.detroit.repository.sf;

import com.Detriot.detroit.entity.sf.SustainabilityMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SustainabilityMetricRepository extends JpaRepository<SustainabilityMetric, Long> {
}