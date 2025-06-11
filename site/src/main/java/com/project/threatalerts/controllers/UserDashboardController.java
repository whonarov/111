package com.project.threatalerts.controllers;

import com.project.threatalerts.models.Alert;
import com.project.threatalerts.service.AlertService;
import com.project.threatalerts.service.FeedbackService;
import com.project.threatalerts.service.UserSettings;
import com.project.threatalerts.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserDashboardController {

    @Autowired
    private AlertService alertService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private UserSettingsService userSettingsService;

    @GetMapping("/dashboard")
    public String userDashboard(@RequestParam(required = false) String cityFilter, Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Alert> alerts = alertService.getAlertsByUser(username);
        if (cityFilter != null && !cityFilter.isEmpty()) {
            alerts.removeIf(alert -> !alert.getCity().equalsIgnoreCase(cityFilter));
            model.addAttribute("cityFilter", cityFilter);
        }
        model.addAttribute("alerts", alerts);
        return "user-dashboard";
    }

    @PostMapping("/feedback")
    public String submitFeedback(@RequestParam Long alertId,
                                 @RequestParam(required = false) Integer rating,
                                 @RequestParam(required = false) String comment,
                                 Authentication authentication) {
        String username = authentication.getName();
        feedbackService.submitFeedback(username, alertId, rating, comment);
        return "redirect:/user/dashboard";
    }

    @GetMapping("/settings")
    public String notificationSettings(Model model, Authentication authentication) {
        String username = authentication.getName();
        UserSettings settings = userSettingsService.getSettingsByUsername(username);
        model.addAttribute("settings", settings);
        return "notification-settings";
    }

    @PostMapping("/settings")
    public String updateNotificationSettings(@ModelAttribute UserSettings settings, Authentication authentication) {
        String username = authentication.getName();
        userSettingsService.updateSettings(username, settings);
        return "redirect:/user/settings?success";
    }
}
