package com.project.threatalerts.controllers;

import com.project.threatalerts.models.Alert;
import com.project.threatalerts.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertApiController {

    @Autowired
    private AlertService alertService;

    @Autowired
    private AlertWebSocketController alertWebSocketController;

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Alert createAlert(@RequestBody Alert alert, Principal principal) {
        alert.setCreatedBy(principal.getName());
        Alert createdAlert = alertService.createAlert(alert);
        alertWebSocketController.broadcastAlert(createdAlert);
        return createdAlert;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Alert> updateAlert(@PathVariable Long id, @RequestBody Alert alert) {
        Alert updated = alertService.updateAlert(id, alert);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        alertWebSocketController.broadcastAlert(updated);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        alertService.deleteAlert(id);
        return ResponseEntity.ok().build();
    }
}
