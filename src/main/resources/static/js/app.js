const socket = new WebSocket('ws://' + window.location.host + '/ws/status');
const timeline = document.getElementById('timeline');
const globalStatus = document.getElementById('global-status');
const progressBar = document.getElementById('total-progress');
const stepsCompleted = document.getElementById('steps-completed');
const totalDurationEl = document.getElementById('total-duration');

socket.onmessage = function (event) {
    const steps = JSON.parse(event.data);
    renderSteps(steps);
    updateGlobalStats(steps);
};

function renderSteps(steps) {
    timeline.innerHTML = '';

    // If no steps yet, show a placeholder or nothing
    if (steps.length === 0) {
        return;
    }

    steps.forEach(step => {
        const item = document.createElement('div');
        item.className = `step-item ${step.status.toLowerCase()}`;

        const duration = step.durationMs ? `${(step.durationMs / 1000).toFixed(1)}s` : '';
        const timeDisplay = step.status === 'RUNNING' ? 'In Progress...' : duration;

        item.innerHTML = `
            <div class="step-marker"></div>
            <div class="step-header">
                <div class="step-title">${step.description}</div>
                <div class="step-time">${timeDisplay}</div>
            </div>
            <div class="step-details">
                Status: <span style="color: var(--${getStatusColorVar(step.status)})">${step.status}</span>
                ${step.startTime ? `| Started: ${formatTime(step.startTime)}` : ''}
            </div>
        `;
        timeline.appendChild(item);
    });
}

function updateGlobalStats(steps) {
    const totalSteps = 20; // Hardcoded for this demo, or derive from known job size
    const completed = steps.filter(s => s.status === 'COMPLETED').length;
    const isRunning = steps.some(s => s.status === 'RUNNING');

    // Update Progress Bar
    const progress = (completed / totalSteps) * 100;
    progressBar.style.width = `${progress}%`;
    stepsCompleted.innerText = `${completed} / ${totalSteps} Steps`;

    // Update Global Status Label
    if (isRunning) {
        globalStatus.innerText = 'Run in Progress';
        globalStatus.style.color = 'var(--running-color)';
        globalStatus.style.borderColor = 'var(--running-color)';
    } else if (completed >= totalSteps) {
        globalStatus.innerText = 'Job Completed';
        globalStatus.style.color = 'var(--success-color)';
        globalStatus.style.borderColor = 'var(--success-color)';
    } else if (steps.length > 0 && !isRunning) {
        // Could be failed or partial
        globalStatus.innerText = 'Job Paused/Failed';
        globalStatus.style.color = 'var(--error-color)';
        globalStatus.style.borderColor = 'var(--error-color)';
    }

    // Calculate total duration so far
    if (steps.length > 0) {
        const firstStart = new Date(steps[0].startTime).getTime();
        const lastEnd = steps[steps.length - 1].endTime
            ? new Date(steps[steps.length - 1].endTime).getTime()
            : new Date().getTime();

        const diff = lastEnd - firstStart;
        totalDurationEl.innerText = formatDuration(diff);
    }
}

function getStatusColorVar(status) {
    switch (status) {
        case 'COMPLETED': return 'success-color';
        case 'RUNNING': return 'running-color';
        case 'FAILED': return 'error-color';
        default: return 'text-secondary';
    }
}

function formatTime(isoString) {
    const d = new Date(isoString);
    return d.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' });
}

function formatDuration(ms) {
    const seconds = Math.floor((ms / 1000) % 60);
    const minutes = Math.floor((ms / (1000 * 60)) % 60);
    const hours = Math.floor((ms / (1000 * 60 * 60)) % 24);

    return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
}
