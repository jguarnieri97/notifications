package ar.edu.unlam.tpi.notifications.persistence.dao.impl;

import ar.edu.unlam.tpi.notifications.exceptions.InternalException;
import ar.edu.unlam.tpi.notifications.models.Notification;
import ar.edu.unlam.tpi.notifications.persistence.dao.NotificationDAO;
import ar.edu.unlam.tpi.notifications.persistence.repository.NotificationRepository;
import ar.edu.unlam.tpi.notifications.utils.annotations.DataAccessObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@DataAccessObject
@RequiredArgsConstructor
@Slf4j
public class NotificationDAOImpl implements NotificationDAO{
    private final NotificationRepository repository;

    @Override
    public Notification save (Notification entity) {
        log.info("Creating notification with ID: {}", entity.getId());

        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }
}
