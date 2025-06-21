package ar.edu.unlam.tpi.notifications.service;

import java.util.List;

import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationCreateResponse;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationResponse;

public interface NotificationService {
    
    public NotificationCreateResponse saveNewNotification(NotificationCreateRequest request);
    public List<NotificationResponse> getNotificationsByUserIdAndType(Long userId, String userType);
}
