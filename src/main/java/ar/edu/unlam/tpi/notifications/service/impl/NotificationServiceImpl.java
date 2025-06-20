package ar.edu.unlam.tpi.notifications.service.impl;

import org.springframework.stereotype.Service;

import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequestDto;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationCreateResponseDto;
import ar.edu.unlam.tpi.notifications.models.Notification;
import ar.edu.unlam.tpi.notifications.persistence.dao.NotificationDAO;
import ar.edu.unlam.tpi.notifications.service.NotificationService;
import ar.edu.unlam.tpi.notifications.utils.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final NotificationDAO notificationDAO;


    @Override
    public NotificationCreateResponseDto saveNewNotification(NotificationCreateRequestDto request){
        log.info("Creating new notification with request: {}", request);
        Notification notification  = Converter.convertToEntity(request, Notification.class);        
        Notification result = notificationDAO.save(notification);

        return Converter.convertToDto(result, NotificationCreateResponseDto.class);
    }
    
}
