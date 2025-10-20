package com.aigreentick.notification.controller;

import com.aigreentick.notification.dto.ApiResponse;
import com.aigreentick.notification.dto.EmailNotificationRequest;
import com.aigreentick.notification.dto.NotificationResponse;
import com.aigreentick.notification.dto.PushNotificationRequest;
import com.aigreentick.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * Send email notification
     * POST /api/v1/notifications/email
     */
    @PostMapping("/email")
    public ResponseEntity<ApiResponse<NotificationResponse>> sendEmailNotification(@Valid @RequestBody EmailNotificationRequest request) {
        log.info("Received email notification request for {} recipients", request.getTo().size());
        
        NotificationResponse response = notificationService.sendEmailNotification(request);
        
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .body(ApiResponse.success(response.getMessage(), response));
    }

    /**
     * Send email notification asynchronously
     * POST /api/v1/notifications/email/async
     */
    @PostMapping("/email/async")
    public ResponseEntity<ApiResponse<String>> sendEmailNotificationAsync(@Valid @RequestBody EmailNotificationRequest request) {
        log.info("Received async email notification request for {} recipients", request.getTo().size());
        
        CompletableFuture<NotificationResponse> future = notificationService.sendEmailNotificationAsync(request);
        
        // Return immediately with a processing message
        return ResponseEntity.accepted()
                .body(ApiResponse.success("Email notification is being processed asynchronously"));
    }

    /**
     * Send push notification
     * POST /api/v1/notifications/push
     */
    @PostMapping("/push")
    public ResponseEntity<ApiResponse<NotificationResponse>> sendPushNotification(@Valid @RequestBody PushNotificationRequest request) {
        log.info("Received push notification request for {} devices", request.getDeviceIds().size());
        
        NotificationResponse response = notificationService.sendPushNotification(request);
        
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .body(ApiResponse.success(response.getMessage(), response));
    }

    /**
     * Send push notification asynchronously
     * POST /api/v1/notifications/push/async
     */
    @PostMapping("/push/async")
    public ResponseEntity<ApiResponse<String>> sendPushNotificationAsync(@Valid @RequestBody PushNotificationRequest request) {
        log.info("Received async push notification request for {} devices", request.getDeviceIds().size());
        
        CompletableFuture<NotificationResponse> future = notificationService.sendPushNotificationAsync(request);
        
        // Return immediately with a processing message
        return ResponseEntity.accepted()
                .body(ApiResponse.success("Push notification is being processed asynchronously"));
    }

    /**
     * Send push notification to a topic
     * POST /api/v1/notifications/push/topic
     */
    @PostMapping("/push/topic")
    public ResponseEntity<ApiResponse<NotificationResponse>> sendPushNotificationToTopic(
            @RequestParam String topic,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) String imageUrl) {
        
        log.info("Received push notification request for topic: {}", topic);
        
        NotificationResponse response = notificationService.sendPushNotificationToTopic(topic, title, description, imageUrl);
        
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .body(ApiResponse.success(response.getMessage(), response));
    }

    /**
     * Send push notification to a topic asynchronously
     * POST /api/v1/notifications/push/topic/async
     */
    @PostMapping("/push/topic/async")
    public ResponseEntity<ApiResponse<String>> sendPushNotificationToTopicAsync(
            @RequestParam String topic,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) String imageUrl) {
        
        log.info("Received async push notification request for topic: {}", topic);
        
        CompletableFuture<NotificationResponse> future = notificationService.sendPushNotificationToTopicAsync(topic, title, description, imageUrl);
        
        // Return immediately with a processing message
        return ResponseEntity.accepted()
                .body(ApiResponse.success("Push notification to topic is being processed asynchronously"));
    }

    /**
     * Send both email and push notifications
     * POST /api/v1/notifications/both
     */
    @PostMapping("/both")
    public ResponseEntity<ApiResponse<NotificationResponse>> sendBothNotifications(
            @Valid @RequestBody EmailNotificationRequest emailRequest,
            @Valid @RequestBody PushNotificationRequest pushRequest) {
        
        log.info("Received request to send both email and push notifications");
        
        NotificationResponse response = notificationService.sendBothNotifications(emailRequest, pushRequest);
        
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .body(ApiResponse.success(response.getMessage(), response));
    }

    /**
     * Send both email and push notifications asynchronously
     * POST /api/v1/notifications/both/async
     */
    @PostMapping("/both/async")
    public ResponseEntity<ApiResponse<String>> sendBothNotificationsAsync(
            @Valid @RequestBody EmailNotificationRequest emailRequest,
            @Valid @RequestBody PushNotificationRequest pushRequest) {
        
        log.info("Received async request to send both email and push notifications");
        
        CompletableFuture<NotificationResponse> future = notificationService.sendBothNotificationsAsync(emailRequest, pushRequest);
        
        // Return immediately with a processing message
        return ResponseEntity.accepted()
                .body(ApiResponse.success("Both notifications are being processed asynchronously"));
    }

    /**
     * Validate email address
     * GET /api/v1/notifications/validate/email?email={email}
     */
    @GetMapping("/validate/email")
    public ResponseEntity<ApiResponse<Boolean>> validateEmail(@RequestParam String email) {
        log.info("Validating email address: {}", email);
        
        boolean isValid = notificationService.isValidEmail(email);
        String message = isValid ? "Email address is valid" : "Email address is invalid";
        
        return ResponseEntity.ok(ApiResponse.success(message, isValid));
    }

    /**
     * Validate device token
     * GET /api/v1/notifications/validate/device-token?token={token}
     */
    @GetMapping("/validate/device-token")
    public ResponseEntity<ApiResponse<Boolean>> validateDeviceToken(@RequestParam String token) {
        log.info("Validating device token");
        
        boolean isValid = notificationService.isValidDeviceToken(token);
        String message = isValid ? "Device token is valid" : "Device token is invalid";
        
        return ResponseEntity.ok(ApiResponse.success(message, isValid));
    }

    /**
     * Health check for notification service
     * GET /api/v1/notifications/health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Object>> healthCheck() {
        log.info("Notification service health check");
        return ResponseEntity.ok(ApiResponse.success("Notification service is healthy"));
    }
}