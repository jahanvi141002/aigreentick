package com.aigreentick.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "email_notification_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationLog {

    @Id
    private String id;

    private String to;

    private List<String> cc;

    private String title;

    private String body;

    private Boolean success;

    private String errorMessage;

    @CreatedDate
    private LocalDateTime sentAt;
}


