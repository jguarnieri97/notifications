package ar.edu.unlam.tpi.notifications.service;

import java.util.List;

import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationResponse;

public interface NotificationService {
    
    /**
     * Guarda una nueva notificación basada en la solicitud proporcionada.
     *
     * @param request la solicitud que contiene los detalles de la notificación
     */
    public void saveNewNotification(NotificationCreateRequest request);

    /**
     * Recupera las notificaciones de un usuario según su ID y tipo.
     *
     * @param userId el ID del usuario
     * @param userType el tipo de usuario
     * @return una lista de notificaciones para el usuario especificado
     */
    public List<NotificationResponse> getNotificationsByUserIdAndType(Long userId, String userType);

    /**
     * Marca una notificación como leída según su ID.
     *
     * @param id el ID de la notificación a marcar como leída
     */
    public void markNotificationAsRead(String id);
}
