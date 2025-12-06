package com.flextemps.dashboard.simulation;

import com.flextemps.dashboard.monitor.JobStep;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class ComplexProcessService {

    private final Random random = new Random();

    @SneakyThrows
    @JobStep(description = "Initializing Data Sources")
    public void step01_init() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Validating User Credentials")
    public void step02_validate() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Fetching Remote Configuration")
    public void step03_fetchConfig() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Connecting to Main Database")
    public void step04_connectDB() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Running Pre-flight Checks")
    public void step05_preFlight() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Loading Cache")
    public void step06_loadCache() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Processing Batch A")
    public void step07_processBatchA() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Processing Batch B")
    public void step08_processBatchB() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Calculating Metrics")
    public void step09_calcMetrics() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Generating Preliminary Reports")
    public void step10_genReports() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Optimizing Resource Usage")
    public void step11_optimize() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Archiving Old Logs")
    public void step12_archiveLogs() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Syncing with Cloud Storage")
    public void step13_cloudSync() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Updating Search Index")
    public void step14_updateIndex() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Sending Notifications")
    public void step15_notify() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Cleaning Temporary Files")
    public void step16_cleanup() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Verifying Integrity")
    public void step17_verify() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Finalizing Transaction")
    public void step18_finalize() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Creating Audit Trail")
    public void step19_audit() {
        simulateWork();
    }

    @SneakyThrows
    @JobStep(description = "Compiling Final Summary")
    public void step20_summary() {
        simulateWork();
    }

    private void simulateWork() throws InterruptedException {
        // Sleep between 500ms and 2000ms to simulate work
        TimeUnit.MILLISECONDS.sleep(500 + random.nextInt(1500));
    }
}
