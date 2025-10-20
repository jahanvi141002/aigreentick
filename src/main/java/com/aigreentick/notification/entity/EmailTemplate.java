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

@Document(collection = "email_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate {

    @Id
    private String id;

    @NotBlank(message = "Template name is required")
    @Indexed(unique = true)
    private String name;

    @NotBlank(message = "Template title is required")
    private String title;

    @NotBlank(message = "Template body is required")
    private String body;

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
    public EmailTemplate(String name, String title, String body, String createdBy) {
        this.name = name;
        this.title = title;
        this.body = body;
        this.createdBy = createdBy;
        this.isActive = true;
    }

    // Constructor for updating template
    public EmailTemplate(String name, String title, String body, Boolean isActive, String modifiedBy) {
        this.name = name;
        this.title = title;
        this.body = body;
        this.isActive = isActive;
        this.modifiedBy = modifiedBy;
    }
}
