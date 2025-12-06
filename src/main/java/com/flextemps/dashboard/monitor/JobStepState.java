package com.flextemps.dashboard.monitor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobStepState {
    private String stepName;
    private String description;
    private String status; // PENDING, RUNNING, COMPLETED, FAILED
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long durationMs;
}
