package com.aigreentick.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationRequest {

    @NotEmpty(message = "To recipients are required")
    private List<@Email(message = "Invalid email format for 'to' field") String> to;

    private List<@Email(message = "Invalid email format for 'cc' field") String> cc;

    @NotBlank(message = "Email title is required")
    private String title;

    @NotBlank(message = "Email body is required")
    private String body;
}
