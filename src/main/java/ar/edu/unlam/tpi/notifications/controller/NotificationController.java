package ar.edu.unlam.tpi.notifications.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.notifications.dto.response.GenericResponse;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.http.HttpStatus;


@RequestMapping("notifications/v1/notification")
@Validated
public interface NotificationController {
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create notification")
    GenericResponse<NotificationCreateResponse> createNotification(
        @RequestBody NotificationCreateRequest request
    );
}
