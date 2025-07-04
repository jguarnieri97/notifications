package ar.edu.unlam.tpi.notifications.controller.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlam.tpi.notifications.controller.NotificationController;
import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.notifications.dto.response.GenericResponse;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationResponse;
import ar.edu.unlam.tpi.notifications.service.NotificationService;
import ar.edu.unlam.tpi.notifications.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NotificationControllerImpl implements NotificationController{
    private final NotificationService notificationService;

    @Override
    public GenericResponse<Void> createNotification(NotificationCreateRequest request){
        notificationService.saveNewNotification(request);
        
        return new GenericResponse<>(
            Constants.STATUS_OK,
            Constants.SUCCESS_MESSAGE,
            null);
    }

    @Override
    public GenericResponse<List<NotificationResponse>> getNotificationsByUserIdAndType(Long userId, String userType) {
        List<NotificationResponse> notifications = notificationService.getNotificationsByUserIdAndType(userId, userType);
        return new GenericResponse<>(
            Constants.STATUS_OK,
            Constants.SUCCESS_MESSAGE,
            notifications
        );
    }

    @Override
    public GenericResponse<Void> markNotificationAsRead(String id) {
        notificationService.markNotificationAsRead(id);
        return new GenericResponse<>(
            Constants.STATUS_OK,
            Constants.SUCCESS_MESSAGE,
            null
        );
    }
}
