package com.project.threatalerts.repo;

import com.project.threatalerts.models.AlertFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertFeedbackRepository extends JpaRepository<AlertFeedback, Long> {
}
