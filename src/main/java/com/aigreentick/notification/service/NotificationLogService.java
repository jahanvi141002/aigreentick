package com.aigreentick.notification.service;

import com.aigreentick.notification.entity.EmailNotificationLog;
import com.aigreentick.notification.entity.PushNotificationLog;
import com.aigreentick.notification.repository.EmailNotificationLogRepository;
import com.aigreentick.notification.repository.PushNotificationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationLogService {

    private final EmailNotificationLogRepository emailNotificationLogRepository;
    private final PushNotificationLogRepository pushNotificationLogRepository;

    public void saveEmailLog(String to, List<String> cc, String title, String body, boolean success, String errorMessage) {
        try {
            EmailNotificationLog log = new EmailNotificationLog();
            log.setTo(to);
            log.setCc(cc);
            log.setTitle(title);
            log.setBody(body);
            log.setSuccess(success);
            log.setErrorMessage(errorMessage);
            EmailNotificationLog saved = emailNotificationLogRepository.save(log);
            logDebug("email", saved != null ? saved.getId() : null, success, errorMessage);
        } catch (Exception ex) {
            log.error("Failed to save email notification log for to={} title={}: {}", to, title, ex.getMessage(), ex);
        }
    }

    public void savePushLog(String deviceId, String title, String description, String imageUrl, boolean success, String errorMessage) {
        try {
            PushNotificationLog log = new PushNotificationLog();
            log.setDeviceId(deviceId);
            log.setTitle(title);
            log.setDescription(description);
            log.setImageUrl(imageUrl);
            log.setSuccess(success);
            log.setErrorMessage(errorMessage);
            PushNotificationLog saved = pushNotificationLogRepository.save(log);
            logDebug("push", saved != null ? saved.getId() : null, success, errorMessage);
        } catch (Exception ex) {
            log.error("Failed to save push notification log for deviceId={} title={}: {}", deviceId, title, ex.getMessage(), ex);
        }
    }

    private void logDebug(String type, String id, boolean success, String errorMessage) {
        if (success) {
            log.debug("Saved {} notification log id={} success=true", type, id);
        } else {
            log.debug("Saved {} notification log id={} success=false error={} ", type, id, errorMessage);
        }
    }
}


