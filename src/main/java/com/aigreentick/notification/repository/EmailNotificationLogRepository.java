package com.aigreentick.notification.repository;

import com.aigreentick.notification.entity.EmailNotificationLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotificationLogRepository extends MongoRepository<EmailNotificationLog, String> {
}


