package com.aigreentick.notification.exception;

public class EmailTemplateAlreadyExistsException extends RuntimeException {
    
    public EmailTemplateAlreadyExistsException(String message) {
        super(message);
    }
    
    public EmailTemplateAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
