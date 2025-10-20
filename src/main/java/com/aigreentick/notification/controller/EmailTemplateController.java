package com.aigreentick.notification.controller;

import com.aigreentick.notification.dto.ApiResponse;
import com.aigreentick.notification.dto.EmailTemplateRequest;
import com.aigreentick.notification.dto.EmailTemplateResponse;
import com.aigreentick.notification.dto.EmailTemplateUpdateRequest;
import com.aigreentick.notification.service.EmailTemplateService;
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
@RequestMapping("/email-templates")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class EmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    /**
     * Create a new email template
     * POST /api/v1/email-templates
     */
    @PostMapping
    public ResponseEntity<ApiResponse<EmailTemplateResponse>> createTemplate(@Valid @RequestBody EmailTemplateRequest request) {
        log.info("Creating new email template: {}", request.getName());
        EmailTemplateResponse response = emailTemplateService.createTemplate(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Email template created successfully", response));
    }

    /**
     * Get all email templates with pagination
     * GET /api/v1/email-templates?page=0&size=10&sort=name,asc
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<EmailTemplateResponse>>> getAllTemplates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        log.info("Fetching all email templates - page: {}, size: {}, sortBy: {}, sortDir: {}", page, size, sortBy, sortDir);
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<EmailTemplateResponse> templates = emailTemplateService.getAllTemplates(pageable);
        return ResponseEntity.ok(ApiResponse.success("Email templates retrieved successfully", templates));
    }

    /**
     * Get all email templates without pagination
     * GET /api/v1/email-templates/all
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<EmailTemplateResponse>>> getAllTemplatesWithoutPagination() {
        log.info("Fetching all email templates without pagination");
        List<EmailTemplateResponse> templates = emailTemplateService.getAllTemplates();
        return ResponseEntity.ok(ApiResponse.success("All email templates retrieved successfully", templates));
    }

    /**
     * Get email template by ID
     * GET /api/v1/email-templates/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmailTemplateResponse>> getTemplateById(@PathVariable String id) {
        log.info("Fetching email template with ID: {}", id);
        EmailTemplateResponse template = emailTemplateService.getTemplateById(id);
        return ResponseEntity.ok(ApiResponse.success("Email template retrieved successfully", template));
    }

    /**
     * Get email template by name
     * GET /api/v1/email-templates/name/{name}
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<EmailTemplateResponse>> getTemplateByName(@PathVariable String name) {
        log.info("Fetching email template with name: {}", name);
        EmailTemplateResponse template = emailTemplateService.getTemplateByName(name);
        return ResponseEntity.ok(ApiResponse.success("Email template retrieved successfully", template));
    }

    /**
     * Get all active email templates
     * GET /api/v1/email-templates/active
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<EmailTemplateResponse>>> getActiveTemplates() {
        log.info("Fetching all active email templates");
        List<EmailTemplateResponse> templates = emailTemplateService.getActiveTemplates();
        return ResponseEntity.ok(ApiResponse.success("Active email templates retrieved successfully", templates));
    }

    /**
     * Get all inactive email templates
     * GET /api/v1/email-templates/inactive
     */
    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<List<EmailTemplateResponse>>> getInactiveTemplates() {
        log.info("Fetching all inactive email templates");
        List<EmailTemplateResponse> templates = emailTemplateService.getInactiveTemplates();
        return ResponseEntity.ok(ApiResponse.success("Inactive email templates retrieved successfully", templates));
    }

    /**
     * Search templates by name
     * GET /api/v1/email-templates/search/name?q={query}
     */
    @GetMapping("/search/name")
    public ResponseEntity<ApiResponse<List<EmailTemplateResponse>>> searchTemplatesByName(@RequestParam String q) {
        log.info("Searching email templates by name: {}", q);
        List<EmailTemplateResponse> templates = emailTemplateService.searchTemplatesByName(q);
        return ResponseEntity.ok(ApiResponse.success("Search results retrieved successfully", templates));
    }

    /**
     * Search templates by title
     * GET /api/v1/email-templates/search/title?q={query}
     */
    @GetMapping("/search/title")
    public ResponseEntity<ApiResponse<List<EmailTemplateResponse>>> searchTemplatesByTitle(@RequestParam String q) {
        log.info("Searching email templates by title: {}", q);
        List<EmailTemplateResponse> templates = emailTemplateService.searchTemplatesByTitle(q);
        return ResponseEntity.ok(ApiResponse.success("Search results retrieved successfully", templates));
    }

    /**
     * Update email template
     * PUT /api/v1/email-templates/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmailTemplateResponse>> updateTemplate(
            @PathVariable String id, 
            @Valid @RequestBody EmailTemplateUpdateRequest request) {
        log.info("Updating email template with ID: {}", id);
        EmailTemplateResponse response = emailTemplateService.updateTemplate(id, request);
        return ResponseEntity.ok(ApiResponse.success("Email template updated successfully", response));
    }

    /**
     * Activate email template
     * PATCH /api/v1/email-templates/{id}/activate
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<EmailTemplateResponse>> activateTemplate(
            @PathVariable String id, 
            @RequestParam String modifiedBy) {
        log.info("Activating email template with ID: {}", id);
        EmailTemplateResponse response = emailTemplateService.activateTemplate(id, modifiedBy);
        return ResponseEntity.ok(ApiResponse.success("Email template activated successfully", response));
    }

    /**
     * Deactivate email template
     * PATCH /api/v1/email-templates/{id}/deactivate
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<EmailTemplateResponse>> deactivateTemplate(
            @PathVariable String id, 
            @RequestParam String modifiedBy) {
        log.info("Deactivating email template with ID: {}", id);
        EmailTemplateResponse response = emailTemplateService.deactivateTemplate(id, modifiedBy);
        return ResponseEntity.ok(ApiResponse.success("Email template deactivated successfully", response));
    }

    /**
     * Delete email template
     * DELETE /api/v1/email-templates/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteTemplate(@PathVariable String id) {
        log.info("Deleting email template with ID: {}", id);
        emailTemplateService.deleteTemplate(id);
        return ResponseEntity.ok(ApiResponse.success("Email template deleted successfully"));
    }

    /**
     * Get template statistics
     * GET /api/v1/email-templates/statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<EmailTemplateService.TemplateStatistics>> getTemplateStatistics() {
        log.info("Fetching email template statistics");
        EmailTemplateService.TemplateStatistics statistics = emailTemplateService.getTemplateStatistics();
        return ResponseEntity.ok(ApiResponse.success("Template statistics retrieved successfully", statistics));
    }

    /**
     * Health check for email templates
     * GET /api/v1/email-templates/health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Object>> healthCheck() {
        log.info("Email template service health check");
        return ResponseEntity.ok(ApiResponse.success("Email template service is healthy"));
    }
}
