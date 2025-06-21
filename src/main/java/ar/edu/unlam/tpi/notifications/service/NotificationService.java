package ar.edu.unlam.tpi.notifications.service;

import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationCreateResponse;

public interface NotificationService {
    
    public NotificationCreateResponse saveNewNotification(NotificationCreateRequest request);
}
