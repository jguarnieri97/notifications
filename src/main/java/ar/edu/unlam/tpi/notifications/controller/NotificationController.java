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

/**
 * Controlador para operaciones relacionadas con notificaciones.
 */
@RequestMapping("notifications/v1/notification")
@Validated
public interface NotificationController {
    
    /**
     * Crea una nueva notificación a partir de la solicitud recibida.
     *
     * @param request la solicitud con los datos de la notificación a crear
     * @return respuesta genérica indicando el resultado de la operación
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create notification")
    GenericResponse<Void> createNotification(
        @RequestBody NotificationCreateRequest request
    );

    /**
     * Obtiene las notificaciones de un usuario según su ID y tipo.
     *
     * @param userId el ID del usuario
     * @param userType el tipo de usuario
     * @return respuesta genérica con la lista de notificaciones encontradas
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get notifications by user id and type")
    GenericResponse<List<NotificationResponse>> getNotificationsByUserIdAndType(
        @RequestParam Long userId, @RequestParam String userType
    );

    /**
     * Marca una notificación como leída según su ID.
     *
     * @param id el identificador de la notificación a marcar como leída
     * @return respuesta genérica indicando el resultado de la operación
     */
    @PostMapping("/read/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Mark notification as read")
    GenericResponse<Void> markNotificationAsRead(@PathVariable String id);
}
