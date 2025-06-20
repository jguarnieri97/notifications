package ar.edu.unlam.tpi.notifications.service;

import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequestDto;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationCreateResponseDto;

public interface NotificationService {
    
    public NotificationCreateResponseDto saveNewNotification(NotificationCreateRequestDto request);
}
