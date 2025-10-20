package com.aigreentick.notification.exception;

public class PushNotificationTemplateAlreadyExistsException extends RuntimeException {
    
    public PushNotificationTemplateAlreadyExistsException(String message) {
        super(message);
    }
    
    public PushNotificationTemplateAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
