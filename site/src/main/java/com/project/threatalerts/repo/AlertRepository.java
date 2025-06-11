package com.project.threatalerts.repo;

import com.project.threatalerts.models.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findAllByOrderByCityAscTimestampDesc();
    List<Alert> findByCreatedByOrderByTimestampDesc(String createdBy);
}
