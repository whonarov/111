package com.project.threatalerts.controllers;

import com.project.threatalerts.models.Alert;
import com.project.threatalerts.service.AlertService;
import com.project.threatalerts.service.AlertSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AlertService alertService;

    @Autowired
    private AlertSubmissionService alertSubmissionService;

    @GetMapping("/panel")
    public String adminPanel(Model model) {
        model.addAttribute("alerts", alertService.getAllAlerts());
        model.addAttribute("writingDisabled", alertSubmissionService.isWritingDisabled());
        return "admin-panel";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createAlert(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String city,
            @RequestParam(defaultValue="GENERAL") String category,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) String videoUrl
    ) {
        if (alertSubmissionService.isWritingDisabled()) {
            return "redirect:/admin/panel?error=writingDisabled";
        }
        Alert alert = new Alert(title, description, city, category, imageUrl, videoUrl);
        alertService.createAlert(alert);
        return "redirect:/admin/panel";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update/{id}")
    public String updateAlert(@PathVariable Long id,
                              @RequestParam String title,
                              @RequestParam String description,
                              @RequestParam String city,
                              @RequestParam String status,
                              @RequestParam(defaultValue="GENERAL") String category,
                              @RequestParam(required = false) String imageUrl,
                              @RequestParam(required = false) String videoUrl) {
        Alert updated = new Alert(title, description, city, category, imageUrl, videoUrl);
        updated.setStatus(status);
        alertService.updateAlert(id, updated);
        return "redirect:/admin/panel";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteAlert(@PathVariable Long id) {
        alertService.deleteAlert(id);
        return "redirect:/admin/panel";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/toggleWriting")
    public String toggleWriting() {
        alertSubmissionService.toggleWriting();
        return "redirect:/admin/panel";
    }
}
