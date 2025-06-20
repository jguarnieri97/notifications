package ar.edu.unlam.tpi.notifications.persistence.dao;

import ar.edu.unlam.tpi.notifications.models.Notification;

public interface NotificationDAO {

    /**
     * Guarda una nueva notificacion o actualiza una existente.
     *
     * @param entity la entidad a guardar.
     * @return la entidad guardada.
     */
    Notification save(Notification entity);
}
