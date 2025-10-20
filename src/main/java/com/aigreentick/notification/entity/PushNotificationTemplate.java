package com.aigreentick.notification.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "push_notification_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationTemplate {

    @Id
    private String id;

    @NotBlank(message = "Template title is required")
    @Indexed(unique = true)
    private String title;

    @NotBlank(message = "Template description is required")
    private String description;

    private String imageUrl;

    @NotNull(message = "IsActive status is required")
    private Boolean isActive = true;

    @NotBlank(message = "Created by is required")
    private String createdBy;

    private String modifiedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Version
    private Long version;

    // Constructor for creating new template
    public PushNotificationTemplate(String title, String description, String imageUrl, String createdBy) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.isActive = true;
    }

    // Constructor for updating template
    public PushNotificationTemplate(String title, String description, String imageUrl, Boolean isActive, String modifiedBy) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.modifiedBy = modifiedBy;
    }
}
