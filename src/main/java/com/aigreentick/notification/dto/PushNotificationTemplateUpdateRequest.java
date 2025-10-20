package com.aigreentick.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationTemplateUpdateRequest {

    @NotBlank(message = "Template title is required")
    private String title;

    @NotBlank(message = "Template description is required")
    private String description;

    private String imageUrl;

    private Boolean isActive;

    @NotBlank(message = "Modified by is required")
    private String modifiedBy;
}
