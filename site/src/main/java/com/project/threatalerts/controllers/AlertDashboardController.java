package com.project.threatalerts.controllers;

import com.project.threatalerts.models.Alert;
import com.project.threatalerts.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AlertDashboardController {

    @Autowired
    private AlertService alertService;
    @GetMapping("/dashboard")
    public String showAlertDashboard(Model model) {
        model.addAttribute("alerts", alertService.getAllAlerts());
        return "alert-dashboard";
    }
    @GetMapping("/alerts")
    public String showAlerts(Model model, Principal principal) {
        List<Alert> alerts = alertService.getAlertsByUser(principal.getName());
        Map<String, List<Alert>> groupedAlerts = alerts.stream()
                .collect(Collectors.groupingBy(Alert::getCity, LinkedHashMap::new, Collectors.toList()));
        model.addAttribute("groupedAlerts", groupedAlerts);
        return "alerts";
    }
}
