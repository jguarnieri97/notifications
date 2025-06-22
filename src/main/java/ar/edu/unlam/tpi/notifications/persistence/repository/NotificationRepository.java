package ar.edu.unlam.tpi.notifications.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.unlam.tpi.notifications.models.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String>{
    List<Notification> findByUserIdAndUserType(Long userId, String userType);
}   
