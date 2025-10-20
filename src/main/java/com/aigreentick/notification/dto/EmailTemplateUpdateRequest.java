package com.aigreentick.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateUpdateRequest {

    @NotBlank(message = "Template name is required")
    private String name;

    @NotBlank(message = "Template title is required")
    private String title;

    @NotBlank(message = "Template body is required")
    private String body;

    private Boolean isActive;

    @NotBlank(message = "Modified by is required")
    private String modifiedBy;
}
