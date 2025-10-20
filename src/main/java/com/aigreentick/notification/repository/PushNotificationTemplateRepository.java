package com.aigreentick.notification.repository;

import com.aigreentick.notification.entity.PushNotificationTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PushNotificationTemplateRepository extends MongoRepository<PushNotificationTemplate, String> {

    /**
     * Find template by title (case-insensitive)
     */
    Optional<PushNotificationTemplate> findByTitleIgnoreCase(String title);

    /**
     * Check if template exists by title (case-insensitive)
     */
    boolean existsByTitleIgnoreCase(String title);

    /**
     * Find all active templates
     */
    List<PushNotificationTemplate> findByIsActiveTrue();

    /**
     * Find all inactive templates
     */
    List<PushNotificationTemplate> findByIsActiveFalse();

    /**
     * Find templates by created by user
     */
    List<PushNotificationTemplate> findByCreatedBy(String createdBy);

    /**
     * Find templates by modified by user
     */
    List<PushNotificationTemplate> findByModifiedBy(String modifiedBy);

    /**
     * Search templates by title containing (case-insensitive)
     */
    List<PushNotificationTemplate> findByTitleContainingIgnoreCase(String title);

    /**
     * Search templates by description containing (case-insensitive)
     */
    List<PushNotificationTemplate> findByDescriptionContainingIgnoreCase(String description);

    /**
     * Find active templates by title containing (case-insensitive)
     */
    @Query("{ 'isActive': true, 'title': { $regex: ?0, $options: 'i' } }")
    List<PushNotificationTemplate> findActiveTemplatesByTitleContaining(String title);

    /**
     * Find active templates by description containing (case-insensitive)
     */
    @Query("{ 'isActive': true, 'description': { $regex: ?0, $options: 'i' } }")
    List<PushNotificationTemplate> findActiveTemplatesByDescriptionContaining(String description);

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

    /**
     * Find templates with image URL
     */
    List<PushNotificationTemplate> findByImageUrlIsNotNull();

    /**
     * Find templates without image URL
     */
    List<PushNotificationTemplate> findByImageUrlIsNull();
}
