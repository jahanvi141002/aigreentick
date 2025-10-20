package com.aigreentick.notification.controller;

import com.aigreentick.notification.dto.ApiResponse;
import com.aigreentick.notification.dto.PushNotificationTemplateRequest;
import com.aigreentick.notification.dto.PushNotificationTemplateResponse;
import com.aigreentick.notification.dto.PushNotificationTemplateUpdateRequest;
import com.aigreentick.notification.service.PushNotificationTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/push-notification-templates")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PushNotificationTemplateController {

    private final PushNotificationTemplateService pushNotificationTemplateService;

    /**
     * Create a new push notification template
     * POST /api/v1/push-notification-templates
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PushNotificationTemplateResponse>> createTemplate(@Valid @RequestBody PushNotificationTemplateRequest request) {
        log.info("Creating new push notification template: {}", request.getTitle());
        PushNotificationTemplateResponse response = pushNotificationTemplateService.createTemplate(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Push notification template created successfully", response));
    }

    /**
     * Get all push notification templates with pagination
     * GET /api/v1/push-notification-templates?page=0&size=10&sort=title,asc
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PushNotificationTemplateResponse>>> getAllTemplates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        log.info("Fetching all push notification templates - page: {}, size: {}, sortBy: {}, sortDir: {}", page, size, sortBy, sortDir);
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<PushNotificationTemplateResponse> templates = pushNotificationTemplateService.getAllTemplates(pageable);
        return ResponseEntity.ok(ApiResponse.success("Push notification templates retrieved successfully", templates));
    }

    /**
     * Get all push notification templates without pagination
     * GET /api/v1/push-notification-templates/all
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PushNotificationTemplateResponse>>> getAllTemplatesWithoutPagination() {
        log.info("Fetching all push notification templates without pagination");
        List<PushNotificationTemplateResponse> templates = pushNotificationTemplateService.getAllTemplates();
        return ResponseEntity.ok(ApiResponse.success("All push notification templates retrieved successfully", templates));
    }

    /**
     * Get push notification template by ID
     * GET /api/v1/push-notification-templates/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PushNotificationTemplateResponse>> getTemplateById(@PathVariable String id) {
        log.info("Fetching push notification template with ID: {}", id);
        PushNotificationTemplateResponse template = pushNotificationTemplateService.getTemplateById(id);
        return ResponseEntity.ok(ApiResponse.success("Push notification template retrieved successfully", template));
    }

    /**
     * Get push notification template by title
     * GET /api/v1/push-notification-templates/title/{title}
     */
    @GetMapping("/title/{title}")
    public ResponseEntity<ApiResponse<PushNotificationTemplateResponse>> getTemplateByTitle(@PathVariable String title) {
        log.info("Fetching push notification template with title: {}", title);
        PushNotificationTemplateResponse template = pushNotificationTemplateService.getTemplateByTitle(title);
        return ResponseEntity.ok(ApiResponse.success("Push notification template retrieved successfully", template));
    }

    /**
     * Get all active push notification templates
     * GET /api/v1/push-notification-templates/active
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<PushNotificationTemplateResponse>>> getActiveTemplates() {
        log.info("Fetching all active push notification templates");
        List<PushNotificationTemplateResponse> templates = pushNotificationTemplateService.getActiveTemplates();
        return ResponseEntity.ok(ApiResponse.success("Active push notification templates retrieved successfully", templates));
    }

    /**
     * Get all inactive push notification templates
     * GET /api/v1/push-notification-templates/inactive
     */
    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<List<PushNotificationTemplateResponse>>> getInactiveTemplates() {
        log.info("Fetching all inactive push notification templates");
        List<PushNotificationTemplateResponse> templates = pushNotificationTemplateService.getInactiveTemplates();
        return ResponseEntity.ok(ApiResponse.success("Inactive push notification templates retrieved successfully", templates));
    }

    /**
     * Search templates by title
     * GET /api/v1/push-notification-templates/search/title?q={query}
     */
    @GetMapping("/search/title")
    public ResponseEntity<ApiResponse<List<PushNotificationTemplateResponse>>> searchTemplatesByTitle(@RequestParam String q) {
        log.info("Searching push notification templates by title: {}", q);
        List<PushNotificationTemplateResponse> templates = pushNotificationTemplateService.searchTemplatesByTitle(q);
        return ResponseEntity.ok(ApiResponse.success("Search results retrieved successfully", templates));
    }

    /**
     * Search templates by description
     * GET /api/v1/push-notification-templates/search/description?q={query}
     */
    @GetMapping("/search/description")
    public ResponseEntity<ApiResponse<List<PushNotificationTemplateResponse>>> searchTemplatesByDescription(@RequestParam String q) {
        log.info("Searching push notification templates by description: {}", q);
        List<PushNotificationTemplateResponse> templates = pushNotificationTemplateService.searchTemplatesByDescription(q);
        return ResponseEntity.ok(ApiResponse.success("Search results retrieved successfully", templates));
    }

    /**
     * Get templates with image URL
     * GET /api/v1/push-notification-templates/with-image
     */
    @GetMapping("/with-image")
    public ResponseEntity<ApiResponse<List<PushNotificationTemplateResponse>>> getTemplatesWithImage() {
        log.info("Fetching push notification templates with image URL");
        List<PushNotificationTemplateResponse> templates = pushNotificationTemplateService.getTemplatesWithImage();
        return ResponseEntity.ok(ApiResponse.success("Templates with image retrieved successfully", templates));
    }

    /**
     * Get templates without image URL
     * GET /api/v1/push-notification-templates/without-image
     */
    @GetMapping("/without-image")
    public ResponseEntity<ApiResponse<List<PushNotificationTemplateResponse>>> getTemplatesWithoutImage() {
        log.info("Fetching push notification templates without image URL");
        List<PushNotificationTemplateResponse> templates = pushNotificationTemplateService.getTemplatesWithoutImage();
        return ResponseEntity.ok(ApiResponse.success("Templates without image retrieved successfully", templates));
    }

    /**
     * Update push notification template
     * PUT /api/v1/push-notification-templates/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PushNotificationTemplateResponse>> updateTemplate(
            @PathVariable String id, 
            @Valid @RequestBody PushNotificationTemplateUpdateRequest request) {
        log.info("Updating push notification template with ID: {}", id);
        PushNotificationTemplateResponse response = pushNotificationTemplateService.updateTemplate(id, request);
        return ResponseEntity.ok(ApiResponse.success("Push notification template updated successfully", response));
    }

    /**
     * Activate push notification template
     * PATCH /api/v1/push-notification-templates/{id}/activate
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<PushNotificationTemplateResponse>> activateTemplate(
            @PathVariable String id, 
            @RequestParam String modifiedBy) {
        log.info("Activating push notification template with ID: {}", id);
        PushNotificationTemplateResponse response = pushNotificationTemplateService.activateTemplate(id, modifiedBy);
        return ResponseEntity.ok(ApiResponse.success("Push notification template activated successfully", response));
    }

    /**
     * Deactivate push notification template
     * PATCH /api/v1/push-notification-templates/{id}/deactivate
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<PushNotificationTemplateResponse>> deactivateTemplate(
            @PathVariable String id, 
            @RequestParam String modifiedBy) {
        log.info("Deactivating push notification template with ID: {}", id);
        PushNotificationTemplateResponse response = pushNotificationTemplateService.deactivateTemplate(id, modifiedBy);
        return ResponseEntity.ok(ApiResponse.success("Push notification template deactivated successfully", response));
    }

    /**
     * Delete push notification template
     * DELETE /api/v1/push-notification-templates/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteTemplate(@PathVariable String id) {
        log.info("Deleting push notification template with ID: {}", id);
        pushNotificationTemplateService.deleteTemplate(id);
        return ResponseEntity.ok(ApiResponse.success("Push notification template deleted successfully"));
    }

    /**
     * Get template statistics
     * GET /api/v1/push-notification-templates/statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<PushNotificationTemplateService.TemplateStatistics>> getTemplateStatistics() {
        log.info("Fetching push notification template statistics");
        PushNotificationTemplateService.TemplateStatistics statistics = pushNotificationTemplateService.getTemplateStatistics();
        return ResponseEntity.ok(ApiResponse.success("Template statistics retrieved successfully", statistics));
    }

    /**
     * Health check for push notification templates
     * GET /api/v1/push-notification-templates/health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Object>> healthCheck() {
        log.info("Push notification template service health check");
        return ResponseEntity.ok(ApiResponse.success("Push notification template service is healthy"));
    }
}
