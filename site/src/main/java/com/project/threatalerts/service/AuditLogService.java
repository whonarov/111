package com.project.threatalerts.service;

import com.project.threatalerts.models.AuditLog;
import com.project.threatalerts.repo.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void log(String username, String action) {
        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        auditLogRepository.save(log);
    }
}
