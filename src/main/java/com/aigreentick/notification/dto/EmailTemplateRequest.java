package com.aigreentick.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateRequest {

    @NotBlank(message = "Template name is required")
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
}
