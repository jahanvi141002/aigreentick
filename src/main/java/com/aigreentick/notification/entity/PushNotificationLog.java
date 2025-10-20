package com.aigreentick.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "push_notification_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationLog {

    @Id
    private String id;

    private String deviceId;

    private String title;

    private String description;

    private String imageUrl;

    private Boolean success;

    private String errorMessage;

    @CreatedDate
    private LocalDateTime sentAt;
}


