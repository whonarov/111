package com.project.threatalerts.repo;

import com.project.threatalerts.models.User;
import com.project.threatalerts.service.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {
    UserSettings findByUser(User user);
}
