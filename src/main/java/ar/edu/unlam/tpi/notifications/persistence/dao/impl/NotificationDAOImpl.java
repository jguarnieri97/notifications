package ar.edu.unlam.tpi.notifications.persistence.dao.impl;

import java.util.List;

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

    @Override
    public List<Notification> findByUserIdAndUserType(Long userId, String userType) {
        log.info("Searching notifications for user ID: {} and user type: {}", userId, userType);
        
        try {
            return repository.findByUserIdAndUserType(userId, userType);
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }
}
