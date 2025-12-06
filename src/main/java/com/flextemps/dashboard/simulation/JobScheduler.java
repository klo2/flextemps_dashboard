package com.flextemps.dashboard.simulation;

import com.flextemps.dashboard.monitor.JobStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final ComplexProcessService processService;
    private final JobStateService jobStateService;

    // Run every 30 minutes, but also run after a short initial delay for demo
    // purposes
    @Scheduled(fixedRate = 30000, initialDelay = 5000)
    public void runScheduledJob() {
        log.info("Starting scheduled job...");
        jobStateService.clear();

        processService.step01_init();
        processService.step02_validate();
        processService.step03_fetchConfig();
        processService.step04_connectDB();
        processService.step05_preFlight();
        processService.step06_loadCache();
        processService.step07_processBatchA();
        processService.step08_processBatchB();
        processService.step09_calcMetrics();
        processService.step10_genReports();
        processService.step11_optimize();
        processService.step12_archiveLogs();
        processService.step13_cloudSync();
        processService.step14_updateIndex();
        processService.step15_notify();
        processService.step16_cleanup();
        processService.step17_verify();
        processService.step18_finalize();
        processService.step19_audit();
        processService.step20_summary();

        log.info("Scheduled job finished.");
    }
}
