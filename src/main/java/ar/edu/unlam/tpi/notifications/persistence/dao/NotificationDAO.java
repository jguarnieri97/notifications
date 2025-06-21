package ar.edu.unlam.tpi.notifications.persistence.dao;

import java.util.List;

import ar.edu.unlam.tpi.notifications.models.Notification;

public interface NotificationDAO {

    /**
     * Guarda una nueva notificacion o actualiza una existente.
     *
     * @param entity la entidad a guardar.
     * @return la entidad guardada.
     */
    Notification save(Notification entity);


    /**
     * Buscar notificaciones por su user id y user type.
     *
     * @param userId el id del usuario.
     * @param userType el tipo de usuario.
     * @return una lista de notificaciones que coinciden con el user id y user type.
     */
    List<Notification> findByUserIdAndUserType(Long userId, String userType);
}
