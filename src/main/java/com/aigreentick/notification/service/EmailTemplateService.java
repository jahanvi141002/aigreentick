package com.aigreentick.notification.service;

import com.aigreentick.notification.dto.EmailTemplateRequest;
import com.aigreentick.notification.dto.EmailTemplateResponse;
import com.aigreentick.notification.dto.EmailTemplateUpdateRequest;
import com.aigreentick.notification.entity.EmailTemplate;
import com.aigreentick.notification.exception.EmailTemplateNotFoundException;
import com.aigreentick.notification.exception.EmailTemplateAlreadyExistsException;
import com.aigreentick.notification.repository.EmailTemplateRepository;
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
public class EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;

    /**
     * Create a new email template
     */
    public EmailTemplateResponse createTemplate(EmailTemplateRequest request) {
        log.info("Creating new email template with name: {}", request.getName());
        
        // Check if template with same name already exists
        if (emailTemplateRepository.existsByNameIgnoreCase(request.getName())) {
            throw new EmailTemplateAlreadyExistsException("Email template with name '" + request.getName() + "' already exists");
        }

        EmailTemplate template = new EmailTemplate(
            request.getName(),
            request.getTitle(),
            request.getBody(),
            request.getCreatedBy()
        );
        template.setIsActive(request.getIsActive());
        template.setModifiedBy(request.getModifiedBy());

        EmailTemplate savedTemplate = emailTemplateRepository.save(template);
        log.info("Successfully created email template with ID: {}", savedTemplate.getId());
        
        return new EmailTemplateResponse(savedTemplate);
    }

    /**
     * Get all email templates with pagination
     */
    @Transactional(readOnly = true)
    public Page<EmailTemplateResponse> getAllTemplates(Pageable pageable) {
        log.info("Fetching all email templates with pagination");
        Page<EmailTemplate> templates = emailTemplateRepository.findAll(pageable);
        return templates.map(EmailTemplateResponse::new);
    }

    /**
     * Get all email templates without pagination
     */
    @Transactional(readOnly = true)
    public List<EmailTemplateResponse> getAllTemplates() {
        log.info("Fetching all email templates");
        return emailTemplateRepository.findAll()
                .stream()
                .map(EmailTemplateResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get email template by ID
     */
    @Transactional(readOnly = true)
    public EmailTemplateResponse getTemplateById(String id) {
        log.info("Fetching email template with ID: {}", id);
        EmailTemplate template = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new EmailTemplateNotFoundException("Email template with ID " + id + " not found"));
        return new EmailTemplateResponse(template);
    }

    /**
     * Get email template by name
     */
    @Transactional(readOnly = true)
    public EmailTemplateResponse getTemplateByName(String name) {
        log.info("Fetching email template with name: {}", name);
        EmailTemplate template = emailTemplateRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EmailTemplateNotFoundException("Email template with name '" + name + "' not found"));
        return new EmailTemplateResponse(template);
    }

    /**
     * Get all active email templates
     */
    @Transactional(readOnly = true)
    public List<EmailTemplateResponse> getActiveTemplates() {
        log.info("Fetching all active email templates");
        return emailTemplateRepository.findByIsActiveTrue()
                .stream()
                .map(EmailTemplateResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get all inactive email templates
     */
    @Transactional(readOnly = true)
    public List<EmailTemplateResponse> getInactiveTemplates() {
        log.info("Fetching all inactive email templates");
        return emailTemplateRepository.findByIsActiveFalse()
                .stream()
                .map(EmailTemplateResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Search templates by name
     */
    @Transactional(readOnly = true)
    public List<EmailTemplateResponse> searchTemplatesByName(String name) {
        log.info("Searching email templates by name: {}", name);
        return emailTemplateRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(EmailTemplateResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Search templates by title
     */
    @Transactional(readOnly = true)
    public List<EmailTemplateResponse> searchTemplatesByTitle(String title) {
        log.info("Searching email templates by title: {}", title);
        return emailTemplateRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(EmailTemplateResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Update email template
     */
    public EmailTemplateResponse updateTemplate(String id, EmailTemplateUpdateRequest request) {
        log.info("Updating email template with ID: {}", id);
        
        EmailTemplate template = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new EmailTemplateNotFoundException("Email template with ID " + id + " not found"));

        // Check if new name conflicts with existing template (excluding current template)
        if (!template.getName().equalsIgnoreCase(request.getName()) && 
            emailTemplateRepository.existsByNameIgnoreCase(request.getName())) {
            throw new EmailTemplateAlreadyExistsException("Email template with name '" + request.getName() + "' already exists");
        }

        template.setName(request.getName());
        template.setTitle(request.getTitle());
        template.setBody(request.getBody());
        template.setModifiedBy(request.getModifiedBy());
        
        if (request.getIsActive() != null) {
            template.setIsActive(request.getIsActive());
        }

        EmailTemplate updatedTemplate = emailTemplateRepository.save(template);
        log.info("Successfully updated email template with ID: {}", updatedTemplate.getId());
        
        return new EmailTemplateResponse(updatedTemplate);
    }

    /**
     * Activate template
     */
    public EmailTemplateResponse activateTemplate(String id, String modifiedBy) {
        log.info("Activating email template with ID: {}", id);
        
        EmailTemplate template = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new EmailTemplateNotFoundException("Email template with ID " + id + " not found"));
        
        template.setIsActive(true);
        template.setModifiedBy(modifiedBy);
        
        EmailTemplate updatedTemplate = emailTemplateRepository.save(template);
        log.info("Successfully activated email template with ID: {}", updatedTemplate.getId());
        
        return new EmailTemplateResponse(updatedTemplate);
    }

    /**
     * Deactivate template
     */
    public EmailTemplateResponse deactivateTemplate(String id, String modifiedBy) {
        log.info("Deactivating email template with ID: {}", id);
        
        EmailTemplate template = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new EmailTemplateNotFoundException("Email template with ID " + id + " not found"));
        
        template.setIsActive(false);
        template.setModifiedBy(modifiedBy);
        
        EmailTemplate updatedTemplate = emailTemplateRepository.save(template);
        log.info("Successfully deactivated email template with ID: {}", updatedTemplate.getId());
        
        return new EmailTemplateResponse(updatedTemplate);
    }

    /**
     * Delete email template
     */
    public void deleteTemplate(String id) {
        log.info("Deleting email template with ID: {}", id);
        
        if (!emailTemplateRepository.existsById(id)) {
            throw new EmailTemplateNotFoundException("Email template with ID " + id + " not found");
        }
        
        emailTemplateRepository.deleteById(id);
        log.info("Successfully deleted email template with ID: {}", id);
    }

    /**
     * Get template statistics
     */
    @Transactional(readOnly = true)
    public TemplateStatistics getTemplateStatistics() {
        log.info("Fetching email template statistics");
        
        long totalTemplates = emailTemplateRepository.count();
        long activeTemplates = emailTemplateRepository.countByIsActiveTrue();
        long inactiveTemplates = emailTemplateRepository.countByIsActiveFalse();
        
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
