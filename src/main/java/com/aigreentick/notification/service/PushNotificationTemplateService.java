package com.aigreentick.notification.service;

import com.aigreentick.notification.dto.PushNotificationTemplateRequest;
import com.aigreentick.notification.dto.PushNotificationTemplateResponse;
import com.aigreentick.notification.dto.PushNotificationTemplateUpdateRequest;
import com.aigreentick.notification.entity.PushNotificationTemplate;
import com.aigreentick.notification.exception.PushNotificationTemplateNotFoundException;
import com.aigreentick.notification.exception.PushNotificationTemplateAlreadyExistsException;
import com.aigreentick.notification.repository.PushNotificationTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PushNotificationTemplateService {

    private final PushNotificationTemplateRepository pushNotificationTemplateRepository;

    /**
     * Create a new push notification template
     */
    public PushNotificationTemplateResponse createTemplate(PushNotificationTemplateRequest request) {
        log.info("Creating new push notification template with title: {}", request.getTitle());
        
        // Check if template with same title already exists
        if (pushNotificationTemplateRepository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new PushNotificationTemplateAlreadyExistsException("Push notification template with title '" + request.getTitle() + "' already exists");
        }

        PushNotificationTemplate template = new PushNotificationTemplate(
            request.getTitle(),
            request.getDescription(),
            request.getImageUrl(),
            request.getCreatedBy()
        );
        template.setIsActive(request.getIsActive());
        template.setModifiedBy(request.getModifiedBy());

        PushNotificationTemplate savedTemplate = pushNotificationTemplateRepository.save(template);
        log.info("Successfully created push notification template with ID: {}", savedTemplate.getId());
        
        return new PushNotificationTemplateResponse(savedTemplate);
    }

    /**
     * Get all push notification templates with pagination
     */
    @Transactional(readOnly = true)
    public Page<PushNotificationTemplateResponse> getAllTemplates(Pageable pageable) {
        log.info("Fetching all push notification templates with pagination");
        Page<PushNotificationTemplate> templates = pushNotificationTemplateRepository.findAll(pageable);
        return templates.map(PushNotificationTemplateResponse::new);
    }

    /**
     * Get all push notification templates without pagination
     */
    @Transactional(readOnly = true)
    public List<PushNotificationTemplateResponse> getAllTemplates() {
        log.info("Fetching all push notification templates");
        return pushNotificationTemplateRepository.findAll()
                .stream()
                .map(PushNotificationTemplateResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get push notification template by ID
     */
    @Transactional(readOnly = true)
    public PushNotificationTemplateResponse getTemplateById(String id) {
        log.info("Fetching push notification template with ID: {}", id);
        PushNotificationTemplate template = pushNotificationTemplateRepository.findById(id)
                .orElseThrow(() -> new PushNotificationTemplateNotFoundException("Push notification template with ID " + id + " not found"));
        return new PushNotificationTemplateResponse(template);
    }

    /**
     * Get push notification template by title
     */
    @Transactional(readOnly = true)
    public PushNotificationTemplateResponse getTemplateByTitle(String title) {
        log.info("Fetching push notification template with title: {}", title);
        PushNotificationTemplate template = pushNotificationTemplateRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new PushNotificationTemplateNotFoundException("Push notification template with title '" + title + "' not found"));
        return new PushNotificationTemplateResponse(template);
    }

    /**
     * Get all active push notification templates
     */
    @Transactional(readOnly = true)
    public List<PushNotificationTemplateResponse> getActiveTemplates() {
        log.info("Fetching all active push notification templates");
        return pushNotificationTemplateRepository.findByIsActiveTrue()
                .stream()
                .map(PushNotificationTemplateResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get all inactive push notification templates
     */
    @Transactional(readOnly = true)
    public List<PushNotificationTemplateResponse> getInactiveTemplates() {
        log.info("Fetching all inactive push notification templates");
        return pushNotificationTemplateRepository.findByIsActiveFalse()
                .stream()
                .map(PushNotificationTemplateResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Search templates by title
     */
    @Transactional(readOnly = true)
    public List<PushNotificationTemplateResponse> searchTemplatesByTitle(String title) {
        log.info("Searching push notification templates by title: {}", title);
        return pushNotificationTemplateRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(PushNotificationTemplateResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Search templates by description
     */
    @Transactional(readOnly = true)
    public List<PushNotificationTemplateResponse> searchTemplatesByDescription(String description) {
        log.info("Searching push notification templates by description: {}", description);
        return pushNotificationTemplateRepository.findByDescriptionContainingIgnoreCase(description)
                .stream()
                .map(PushNotificationTemplateResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get templates with image URL
     */
    @Transactional(readOnly = true)
    public List<PushNotificationTemplateResponse> getTemplatesWithImage() {
        log.info("Fetching push notification templates with image URL");
        return pushNotificationTemplateRepository.findByImageUrlIsNotNull()
                .stream()
                .map(PushNotificationTemplateResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get templates without image URL
     */
    @Transactional(readOnly = true)
    public List<PushNotificationTemplateResponse> getTemplatesWithoutImage() {
        log.info("Fetching push notification templates without image URL");
        return pushNotificationTemplateRepository.findByImageUrlIsNull()
                .stream()
                .map(PushNotificationTemplateResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Update push notification template
     */
    public PushNotificationTemplateResponse updateTemplate(String id, PushNotificationTemplateUpdateRequest request) {
        log.info("Updating push notification template with ID: {}", id);
        
        PushNotificationTemplate template = pushNotificationTemplateRepository.findById(id)
                .orElseThrow(() -> new PushNotificationTemplateNotFoundException("Push notification template with ID " + id + " not found"));

        // Check if new title conflicts with existing template (excluding current template)
        if (!template.getTitle().equalsIgnoreCase(request.getTitle()) && 
            pushNotificationTemplateRepository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new PushNotificationTemplateAlreadyExistsException("Push notification template with title '" + request.getTitle() + "' already exists");
        }

        template.setTitle(request.getTitle());
        template.setDescription(request.getDescription());
        template.setImageUrl(request.getImageUrl());
        template.setModifiedBy(request.getModifiedBy());
        
        if (request.getIsActive() != null) {
            template.setIsActive(request.getIsActive());
        }

        PushNotificationTemplate updatedTemplate = pushNotificationTemplateRepository.save(template);
        log.info("Successfully updated push notification template with ID: {}", updatedTemplate.getId());
        
        return new PushNotificationTemplateResponse(updatedTemplate);
    }

    /**
     * Activate template
     */
    public PushNotificationTemplateResponse activateTemplate(String id, String modifiedBy) {
        log.info("Activating push notification template with ID: {}", id);
        
        PushNotificationTemplate template = pushNotificationTemplateRepository.findById(id)
                .orElseThrow(() -> new PushNotificationTemplateNotFoundException("Push notification template with ID " + id + " not found"));
        
        template.setIsActive(true);
        template.setModifiedBy(modifiedBy);
        
        PushNotificationTemplate updatedTemplate = pushNotificationTemplateRepository.save(template);
        log.info("Successfully activated push notification template with ID: {}", updatedTemplate.getId());
        
        return new PushNotificationTemplateResponse(updatedTemplate);
    }

    /**
     * Deactivate template
     */
    public PushNotificationTemplateResponse deactivateTemplate(String id, String modifiedBy) {
        log.info("Deactivating push notification template with ID: {}", id);
        
        PushNotificationTemplate template = pushNotificationTemplateRepository.findById(id)
                .orElseThrow(() -> new PushNotificationTemplateNotFoundException("Push notification template with ID " + id + " not found"));
        
        template.setIsActive(false);
        template.setModifiedBy(modifiedBy);
        
        PushNotificationTemplate updatedTemplate = pushNotificationTemplateRepository.save(template);
        log.info("Successfully deactivated push notification template with ID: {}", updatedTemplate.getId());
        
        return new PushNotificationTemplateResponse(updatedTemplate);
    }

    /**
     * Delete push notification template
     */
    public void deleteTemplate(String id) {
        log.info("Deleting push notification template with ID: {}", id);
        
        if (!pushNotificationTemplateRepository.existsById(id)) {
            throw new PushNotificationTemplateNotFoundException("Push notification template with ID " + id + " not found");
        }
        
        pushNotificationTemplateRepository.deleteById(id);
        log.info("Successfully deleted push notification template with ID: {}", id);
    }

    /**
     * Get template statistics
     */
    @Transactional(readOnly = true)
    public TemplateStatistics getTemplateStatistics() {
        log.info("Fetching push notification template statistics");
        
        long totalTemplates = pushNotificationTemplateRepository.count();
        long activeTemplates = pushNotificationTemplateRepository.countByIsActiveTrue();
        long inactiveTemplates = pushNotificationTemplateRepository.countByIsActiveFalse();
        
        return new TemplateStatistics(totalTemplates, activeTemplates, inactiveTemplates);
    }

    /**
     * Inner class for template statistics
     */
    public static class TemplateStatistics {
        private final long totalTemplates;
        private final long activeTemplates;
        private final long inactiveTemplates;

        public TemplateStatistics(long totalTemplates, long activeTemplates, long inactiveTemplates) {
            this.totalTemplates = totalTemplates;
            this.activeTemplates = activeTemplates;
            this.inactiveTemplates = inactiveTemplates;
        }

        public long getTotalTemplates() { return totalTemplates; }
        public long getActiveTemplates() { return activeTemplates; }
        public long getInactiveTemplates() { return inactiveTemplates; }
    }
}
