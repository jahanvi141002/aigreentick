package com.aigreentick.notification.exception;

public class PushNotificationTemplateNotFoundException extends RuntimeException {
    
    public PushNotificationTemplateNotFoundException(String message) {
        super(message);
    }
    
    public PushNotificationTemplateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
