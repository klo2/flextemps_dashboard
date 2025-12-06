package com.flextemps.dashboard.monitor;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class JobStateService {

    private final List<JobStepState> steps = new CopyOnWriteArrayList<>();
    private final List<Runnable> listeners = new CopyOnWriteArrayList<>();

    public void addStep(JobStepState step) {
        steps.add(step);
        notifyListeners();
    }

    public void updateStep(JobStepState step) {
        // In a real DB scanarios, we might save to DB here.
        // For in-memory, we assume the object reference is updated or we replace it.
        // since we are using CopyOnWriteArrayList, replacing is safer if we change
        // reference,
        // but here we likely mutate the object.
        notifyListeners();
    }

    public void clear() {
        steps.clear();
        notifyListeners();
    }

    public List<JobStepState> getSteps() {
        return new ArrayList<>(steps);
    }

    public void registerListener(Runnable listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        listeners.forEach(Runnable::run);
    }
}
