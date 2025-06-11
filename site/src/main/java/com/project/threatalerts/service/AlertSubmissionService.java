package com.project.threatalerts.service;

import org.springframework.stereotype.Service;

@Service
public class AlertSubmissionService {

    private boolean writingDisabled = false;

    public boolean isWritingDisabled() {
        return writingDisabled;
    }

    public void toggleWriting() {
        writingDisabled = !writingDisabled;
    }
}
