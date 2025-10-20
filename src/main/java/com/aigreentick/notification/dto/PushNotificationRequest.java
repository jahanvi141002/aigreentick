package com.aigreentick.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationRequest {

    @NotEmpty(message = "Device IDs are required")
    private List<String> deviceIds;

    @NotBlank(message = "Push notification title is required")
    private String title;

    @NotBlank(message = "Push notification description is required")
    private String description;

    private String imageUrl;
}
