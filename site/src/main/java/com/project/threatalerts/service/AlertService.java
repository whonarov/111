package com.project.threatalerts.service;

import com.project.threatalerts.models.Alert;
import com.project.threatalerts.repo.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public List<Alert> getAllAlerts() {
        return alertRepository.findAllByOrderByCityAscTimestampDesc();
    }

    public List<Alert> getAlertsByUser(String username) {
        return alertRepository.findByCreatedByOrderByTimestampDesc(username);
    }

    public Alert createAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    public Alert updateAlert(Long id, Alert updated) {
        return alertRepository.findById(id).map(alert -> {
            alert.setTitle(updated.getTitle());
            alert.setDescription(updated.getDescription());
            alert.setCity(updated.getCity());
            alert.setStatus(updated.getStatus());
            return alertRepository.save(alert);
        }).orElse(null);
    }

    public void deleteAlert(Long id) {
        alertRepository.deleteById(id);
    }
}
