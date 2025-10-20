package com.aigreentick.notification.repository;

import com.aigreentick.notification.entity.EmailTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, String> {

    /**
     * Find template by name (case-insensitive)
     */
    Optional<EmailTemplate> findByNameIgnoreCase(String name);

    /**
     * Check if template exists by name (case-insensitive)
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Find all active templates
     */
    List<EmailTemplate> findByIsActiveTrue();

    /**
     * Find all inactive templates
     */
    List<EmailTemplate> findByIsActiveFalse();

    /**
     * Find templates by created by user
     */
    List<EmailTemplate> findByCreatedBy(String createdBy);

    /**
     * Find templates by modified by user
     */
    List<EmailTemplate> findByModifiedBy(String modifiedBy);

    /**
     * Search templates by name containing (case-insensitive)
     */
    List<EmailTemplate> findByNameContainingIgnoreCase(String name);

    /**
     * Search templates by title containing (case-insensitive)
     */
    List<EmailTemplate> findByTitleContainingIgnoreCase(String title);

    /**
     * Find active templates by name containing (case-insensitive)
     */
    @Query("{ 'isActive': true, 'name': { $regex: ?0, $options: 'i' } }")
    List<EmailTemplate> findActiveTemplatesByNameContaining(String name);

    /**
     * Count active templates
     */
    long countByIsActiveTrue();

    /**
     * Count inactive templates
     */
    long countByIsActiveFalse();

    /**
     * Count templates by created by user
     */
    long countByCreatedBy(String createdBy);
}
