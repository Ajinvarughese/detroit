package com.Detriot.detroit.sf.service;


import com.Detriot.detroit.sf.entity.SustainabilityMetric;
import com.Detriot.detroit.sf.repository.SustainabilityMetricRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SustainabilityMetricService {

    private final SustainabilityMetricRepository metricRepository;

    public List<SustainabilityMetric> getAllMetrics() {
        return metricRepository.findAll();
    }

    public SustainabilityMetric getMetricById(Long id) {
        return metricRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Metric not found with ID: " + id));
    }

    public SustainabilityMetric createMetric(SustainabilityMetric metric) {
        return metricRepository.save(metric);
    }

    public SustainabilityMetric updateMetric(Long id, SustainabilityMetric updatedMetric) {
        return metricRepository.findById(id)
                .map(metric -> {
                    metric.setMetricName(updatedMetric.getMetricName());
                    metric.setCategory(updatedMetric.getCategory());
                    metric.setUnit(updatedMetric.getUnit());
                    metric.setDescription(updatedMetric.getDescription());
                    return metricRepository.save(metric);
                })
                .orElseThrow(() -> new EntityNotFoundException("Metric not found with ID: " + id));
    }

    public void deleteMetric(Long id) {
        if (!metricRepository.existsById(id)) {
            throw new EntityNotFoundException("Metric not found with ID: " + id);
        }
        metricRepository.deleteById(id);
    }
}
