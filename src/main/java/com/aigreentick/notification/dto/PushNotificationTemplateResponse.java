package com.aigreentick.notification.dto;

import com.aigreentick.notification.entity.PushNotificationTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationTemplateResponse {

    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private Boolean isActive;
    private String createdBy;
    private String modifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long version;

    // Constructor to convert from Entity
    public PushNotificationTemplateResponse(PushNotificationTemplate pushNotificationTemplate) {
        this.id = pushNotificationTemplate.getId();
        this.title = pushNotificationTemplate.getTitle();
        this.description = pushNotificationTemplate.getDescription();
        this.imageUrl = pushNotificationTemplate.getImageUrl();
        this.isActive = pushNotificationTemplate.getIsActive();
        this.createdBy = pushNotificationTemplate.getCreatedBy();
        this.modifiedBy = pushNotificationTemplate.getModifiedBy();
        this.createdAt = pushNotificationTemplate.getCreatedAt();
        this.modifiedAt = pushNotificationTemplate.getModifiedAt();
        this.version = pushNotificationTemplate.getVersion();
    }
}
