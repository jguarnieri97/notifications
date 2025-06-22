package ar.edu.unlam.tpi.notifications.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.notifications.dto.response.GenericResponse;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationResponse;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import org.springframework.http.HttpStatus;


@RequestMapping("notifications/v1/notification")
@Validated
public interface NotificationController {
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create notification")
    GenericResponse<Void> createNotification(
        @RequestBody NotificationCreateRequest request
    );

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get notifications by user id and type")
    GenericResponse<List<NotificationResponse>> getNotificationsByUserIdAndType(
        @RequestParam Long userId, @RequestParam String userType
    );

    @PostMapping("/read/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Mark notification as read")
    GenericResponse<Void> markNotificationAsRead(@PathVariable String id);
}
