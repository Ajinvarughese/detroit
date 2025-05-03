package com.Detriot.detroit.controller.sf;

import com.Detriot.detroit.entity.sf.SustainabilityMetric;
import com.Detriot.detroit.service.sf.SustainabilityMetricService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metrics")
public class SustainabilityMetricController {

    private final SustainabilityMetricService metricService;

    public SustainabilityMetricController(SustainabilityMetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping
    public ResponseEntity<List<SustainabilityMetric>> getAllMetrics() {
        return ResponseEntity.ok(metricService.getAllMetrics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SustainabilityMetric> getMetricById(@PathVariable Long id) {
        return ResponseEntity.ok(metricService.getMetricById(id));
    }

    @PostMapping
    public ResponseEntity<SustainabilityMetric> createMetric(@RequestBody SustainabilityMetric metric) {
        return ResponseEntity.ok(metricService.createMetric(metric));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SustainabilityMetric> updateMetric(@PathVariable Long id, @RequestBody SustainabilityMetric metric) {
        return ResponseEntity.ok(metricService.updateMetric(id, metric));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetric(@PathVariable Long id) {
        metricService.deleteMetric(id);
        return ResponseEntity.noContent().build();
    }
}