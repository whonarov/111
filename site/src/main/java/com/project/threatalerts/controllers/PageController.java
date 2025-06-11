package com.project.threatalerts.controllers;

import com.project.threatalerts.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("alerts", alertService.getAllAlerts());
        return "home";
    }

    @GetMapping("/page-alerts")
    public String alertsPage(Model model) {
        model.addAttribute("alerts", alertService.getAllAlerts());
        return "page-alerts";
    }
}
