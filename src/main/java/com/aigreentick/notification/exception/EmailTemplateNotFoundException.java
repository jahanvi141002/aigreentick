package com.aigreentick.notification.exception;

public class EmailTemplateNotFoundException extends RuntimeException {
    
    public EmailTemplateNotFoundException(String message) {
        super(message);
    }
    
    public EmailTemplateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
