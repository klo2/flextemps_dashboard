package com.flextemps.dashboard.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flextemps.dashboard.monitor.JobStateService;
import com.flextemps.dashboard.monitor.JobStepState;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class StatusWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final JobStateService jobStateService;
    private final ObjectMapper objectMapper;

    public StatusWebSocketHandler(JobStateService jobStateService) {
        this.jobStateService = jobStateService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                false);
    }

    @PostConstruct
    public void init() {
        jobStateService.registerListener(this::broadcastStatus);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        // Send current state immediately upon connection
        List<JobStepState> currentSteps = jobStateService.getSteps();
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(currentSteps)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status)
            throws Exception {
        sessions.remove(session);
    }

    private void broadcastStatus() {
        List<JobStepState> currentSteps = jobStateService.getSteps();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(currentSteps);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(json));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
