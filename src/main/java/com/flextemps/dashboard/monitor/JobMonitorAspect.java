package com.flextemps.dashboard.monitor;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class JobMonitorAspect {

    private final JobStateService jobStateService;

    @Around("@annotation(jobStep)")
    public Object trackJobStep(ProceedingJoinPoint joinPoint, JobStep jobStep) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String description = jobStep.description().isEmpty() ? methodName : jobStep.description();

        JobStepState state = JobStepState.builder()
                .stepName(methodName)
                .description(description)
                .status("RUNNING")
                .startTime(ZonedDateTime.now())
                .build();

        jobStateService.addStep(state);

        Object result;
        try {
            result = joinPoint.proceed();
            state.setStatus("COMPLETED");
        } catch (Throwable e) {
            state.setStatus("FAILED");
            throw e;
        } finally {
            state.setEndTime(ZonedDateTime.now());
            state.setDurationMs(ChronoUnit.MILLIS.between(state.getStartTime(), state.getEndTime()));
            jobStateService.updateStep(state);
        }

        return result;
    }
}
