package com.project.threatalerts.controllers;

import com.project.threatalerts.models.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class AlertWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void broadcastAlert(Alert alert) {
        if (!"STOPPED".equalsIgnoreCase(alert.getStatus())) {
            messagingTemplate.convertAndSend("/topic/alerts", alert);
        }
    }
}
