package ar.edu.unlam.tpi.notifications.controller.impl;

import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlam.tpi.notifications.controller.NotificationController;
import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.notifications.dto.response.GenericResponse;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationCreateResponse;
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
    public GenericResponse<NotificationCreateResponse>createNotification(NotificationCreateRequest request){
        NotificationCreateResponse response = notificationService.saveNewNotification(request);
        
        return new GenericResponse<>(
            Constants.STATUS_OK,
            Constants.SUCCESS_MESSAGE,
            response);
    }
}
