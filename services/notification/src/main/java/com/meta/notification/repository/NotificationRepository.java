package com.meta.notification.repository;

import com.meta.notification.kafka.payment.PaymentConfirmation;
import com.meta.notification.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification,String> {
}
