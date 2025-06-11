package com.project.threatalerts.service;

import com.project.threatalerts.models.User;
import com.project.threatalerts.repo.UserRepository;
import com.project.threatalerts.repo.UserSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSettingsRepository userSettingsRepository;

    public UserSettings getSettingsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            UserSettings settings = userSettingsRepository.findByUser(user);
            if (settings == null) {
                settings = new UserSettings();
                settings.setUser(user);
                settings.setEmailNotifications(true);
                settings.setSmsNotifications(false);
                settings.setPushNotifications(true);
                settings.setNotificationFrequency("IMMEDIATE");
                userSettingsRepository.save(settings);
            }
            return settings;
        }
        return null;
    }

    public void updateSettings(String username, UserSettings newSettings) {
        UserSettings settings = getSettingsByUsername(username);
        if (settings != null) {
            settings.setEmailNotifications(newSettings.isEmailNotifications());
            settings.setSmsNotifications(newSettings.isSmsNotifications());
            settings.setPushNotifications(newSettings.isPushNotifications());
            settings.setNotificationFrequency(newSettings.getNotificationFrequency());
            settings.setPreferredCities(newSettings.getPreferredCities());
            userSettingsRepository.save(settings);
        }
    }
}
