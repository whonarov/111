package com.project.threatalerts.service;

import com.project.threatalerts.models.Alert;
import com.project.threatalerts.models.AlertFeedback;
import com.project.threatalerts.models.User;

import com.project.threatalerts.repo.AlertFeedbackRepository;
import com.project.threatalerts.repo.AlertRepository;
import com.project.threatalerts.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private AlertFeedbackRepository alertFeedbackRepository;
    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private UserRepository userRepository;

    public void submitFeedback(String username, Long alertId, Integer rating, String comment) {
        User user = userRepository.findByUsername(username).orElse(null);
        Alert alert = alertRepository.findById(alertId).orElse(null);
        if (user != null && alert != null) {
            AlertFeedback feedback = new AlertFeedback();
            feedback.setUser(user);
            feedback.setAlert(alert);
            feedback.setRating(rating);
            feedback.setComment(comment);
            alertFeedbackRepository.save(feedback);
        }
    }

}
