package com.aigreentick.notification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PushNotificationLogRepository extends MongoRepository<com.aigreentick.notification.entity.PushNotificationLog, String> {
}


