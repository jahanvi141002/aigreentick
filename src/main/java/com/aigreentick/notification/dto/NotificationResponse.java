package com.aigreentick.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private boolean success;
    private String message;
    private int successCount;
    private int failureCount;
    private Object data;

    public NotificationResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.successCount = 0;
        this.failureCount = 0;
    }

    public NotificationResponse(boolean success, String message, int successCount, int failureCount) {
        this.success = success;
        this.message = message;
        this.successCount = successCount;
        this.failureCount = failureCount;
    }
}
