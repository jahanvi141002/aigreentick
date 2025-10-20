package com.aigreentick.notification.service;

import com.aigreentick.notification.dto.EmailNotificationRequest;
import com.aigreentick.notification.dto.NotificationResponse;
import com.aigreentick.notification.dto.PushNotificationRequest;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender mailSender;
    private final FirebaseMessaging firebaseMessaging;
    private final NotificationLogService notificationLogService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * Send email notification to multiple recipients
     */
    public NotificationResponse sendEmailNotification(EmailNotificationRequest request) {
        log.info("Sending email notification to {} recipients", request.getTo().size());
        int successCount = 0;
        int failureCount = 0;
        List<String> errors = new ArrayList<>();

        try {
            for (String toEmail : request.getTo()) {
                try {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(toEmail);
                    
                    // Add CC recipients if provided
                    if (request.getCc() != null && !request.getCc().isEmpty()) {
                        message.setCc(request.getCc().toArray(new String[0]));
                    }
                    
                    message.setSubject(request.getTitle());
                    message.setText(request.getBody());
                    
                    mailSender.send(message);
                    successCount++;
                    log.info("Email sent successfully to: {}", toEmail);
                    notificationLogService.saveEmailLog(
                            toEmail,
                            request.getCc(),
                            request.getTitle(),
                            request.getBody(),
                            true,
                            null
                    );
                    
                } catch (Exception e) {
                    failureCount++;
                    String error = "Failed to send email to " + toEmail + ": " + e.getMessage();
                    errors.add(error);
                    log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
                    notificationLogService.saveEmailLog(
                            toEmail,
                            request.getCc(),
                            request.getTitle(),
                            request.getBody(),
                            false,
                            e.getMessage()
                    );
                }
            }

            String message = String.format("Email notification sent. Success: %d, Failed: %d", successCount, failureCount);
            return new NotificationResponse(true, message, successCount, failureCount, errors.isEmpty() ? null : errors);
            
        } catch (Exception e) {
            log.error("Error sending email notification: {}", e.getMessage(), e);
            return new NotificationResponse(false, "Failed to send email notification: " + e.getMessage());
        }
    }

    /**
     * Send email notification asynchronously
     */
    public CompletableFuture<NotificationResponse> sendEmailNotificationAsync(EmailNotificationRequest request) {
        return CompletableFuture.supplyAsync(() -> sendEmailNotification(request), executorService);
    }

    /**
     * Send push notification to multiple devices
     */
    public NotificationResponse sendPushNotification(PushNotificationRequest request) {
        log.info("Sending push notification to {} devices", request.getDeviceIds().size());
        
        int successCount = 0;
        int failureCount = 0;
        List<String> errors = new ArrayList<>();

        try {
            // Create the notification payload
            Notification notification = Notification.builder()
                    .setTitle(request.getTitle())
                    .setBody(request.getDescription())
                    .setImage(request.getImageUrl())
                    .build();

            // Create the message
            Message.Builder messageBuilder = Message.builder()
                    .setNotification(notification)
                    .putData("title", request.getTitle())
                    .putData("body", request.getDescription());

            if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
                messageBuilder.putData("image", request.getImageUrl());
            }

            // Send to each device
            for (String deviceId : request.getDeviceIds()) {
                try {
                    Message message = messageBuilder.setToken(deviceId).build();
                    String response = firebaseMessaging.send(message);
                    successCount++;
                    log.info("Push notification sent successfully to device: {}, response: {}", deviceId, response);
                    notificationLogService.savePushLog(
                            deviceId,
                            request.getTitle(),
                            request.getDescription(),
                            request.getImageUrl(),
                            true,
                            null
                    );
                    
                } catch (FirebaseMessagingException e) {
                    failureCount++;
                    String error = "Failed to send push notification to device " + deviceId + ": " + e.getMessage();
                    errors.add(error);
                    log.error("Failed to send push notification to device {}: {}", deviceId, e.getMessage());
                    notificationLogService.savePushLog(
                            deviceId,
                            request.getTitle(),
                            request.getDescription(),
                            request.getImageUrl(),
                            false,
                            e.getMessage()
                    );
                }
            }

            String message = String.format("Push notification sent. Success: %d, Failed: %d", successCount, failureCount);
            return new NotificationResponse(true, message, successCount, failureCount, errors.isEmpty() ? null : errors);
            
        } catch (Exception e) {
            log.error("Error sending push notification: {}", e.getMessage(), e);
            return new NotificationResponse(false, "Failed to send push notification: " + e.getMessage());
        }
    }

    /**
     * Send push notification asynchronously
     */
    public CompletableFuture<NotificationResponse> sendPushNotificationAsync(PushNotificationRequest request) {
        return CompletableFuture.supplyAsync(() -> sendPushNotification(request), executorService);
    }

    /**
     * Send push notification to a topic
     */
    public NotificationResponse sendPushNotificationToTopic(String topic, String title, String description, String imageUrl) {
        log.info("Sending push notification to topic: {}", topic);
        
        try {
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(description)
                    .setImage(imageUrl)
                    .build();

            Message.Builder messageBuilder = Message.builder()
                    .setTopic(topic)
                    .setNotification(notification)
                    .putData("title", title)
                    .putData("body", description);

            if (imageUrl != null && !imageUrl.isEmpty()) {
                messageBuilder.putData("image", imageUrl);
            }

            String response = firebaseMessaging.send(messageBuilder.build());
            log.info("Push notification sent successfully to topic: {}, response: {}", topic, response);
            
            return new NotificationResponse(true, "Push notification sent successfully to topic: " + topic);
            
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send push notification to topic {}: {}", topic, e.getMessage());
            return new NotificationResponse(false, "Failed to send push notification to topic: " + e.getMessage());
        }
    }

    /**
     * Send push notification to a topic asynchronously
     */
    public CompletableFuture<NotificationResponse> sendPushNotificationToTopicAsync(String topic, String title, String description, String imageUrl) {
        return CompletableFuture.supplyAsync(() -> sendPushNotificationToTopic(topic, title, description, imageUrl), executorService);
    }

    /**
     * Send both email and push notifications
     */
    public NotificationResponse sendBothNotifications(EmailNotificationRequest emailRequest, PushNotificationRequest pushRequest) {
        log.info("Sending both email and push notifications");
        
        NotificationResponse emailResponse = sendEmailNotification(emailRequest);
        NotificationResponse pushResponse = sendPushNotification(pushRequest);
        
        int totalSuccess = emailResponse.getSuccessCount() + pushResponse.getSuccessCount();
        int totalFailure = emailResponse.getFailureCount() + pushResponse.getFailureCount();
        
        String message = String.format("Notifications sent. Email Success: %d, Email Failed: %d, Push Success: %d, Push Failed: %d",
                emailResponse.getSuccessCount(), emailResponse.getFailureCount(),
                pushResponse.getSuccessCount(), pushResponse.getFailureCount());
        
        return new NotificationResponse(true, message, totalSuccess, totalFailure);
    }

    /**
     * Send both email and push notifications asynchronously
     */
    public CompletableFuture<NotificationResponse> sendBothNotificationsAsync(EmailNotificationRequest emailRequest, PushNotificationRequest pushRequest) {
        return CompletableFuture.supplyAsync(() -> sendBothNotifications(emailRequest, pushRequest), executorService);
    }

    /**
     * Validate email addresses
     */
    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Validate device token format (basic validation)
     */
    public boolean isValidDeviceToken(String deviceToken) {
        return deviceToken != null && deviceToken.length() > 10;
    }
}
