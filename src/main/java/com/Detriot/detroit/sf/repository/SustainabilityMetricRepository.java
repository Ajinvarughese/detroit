package com.Detriot.detroit.sf.repository;


import com.Detriot.detroit.sf.entity.SustainabilityMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SustainabilityMetricRepository extends JpaRepository<SustainabilityMetric, Long> {
}