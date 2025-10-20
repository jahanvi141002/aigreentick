package com.aigreentick.notification.dto;

import com.aigreentick.notification.entity.EmailTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateResponse {

    private String id;
    private String name;
    private String title;
    private String body;
    private Boolean isActive;
    private String createdBy;
    private String modifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long version;

    // Constructor to convert from Entity
    public EmailTemplateResponse(EmailTemplate emailTemplate) {
        this.id = emailTemplate.getId();
        this.name = emailTemplate.getName();
        this.title = emailTemplate.getTitle();
        this.body = emailTemplate.getBody();
        this.isActive = emailTemplate.getIsActive();
        this.createdBy = emailTemplate.getCreatedBy();
        this.modifiedBy = emailTemplate.getModifiedBy();
        this.createdAt = emailTemplate.getCreatedAt();
        this.modifiedAt = emailTemplate.getModifiedAt();
        this.version = emailTemplate.getVersion();
    }
}
