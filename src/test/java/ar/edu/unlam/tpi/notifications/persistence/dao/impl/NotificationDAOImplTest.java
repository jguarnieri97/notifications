package ar.edu.unlam.tpi.notifications.persistence.dao.impl;

import ar.edu.unlam.tpi.notifications.exceptions.InternalException;
import ar.edu.unlam.tpi.notifications.exceptions.NotFoundException;
import ar.edu.unlam.tpi.notifications.models.Notification;
import ar.edu.unlam.tpi.notifications.persistence.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationDAOImplTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private NotificationDAOImpl notificationDAO;


    @Test
    void givenValidNotification_whenSave_thenReturnsSavedNotification() {
        Notification notification = new Notification();
        when(repository.save(notification)).thenReturn(notification);
    
        Notification result = notificationDAO.save(notification);
    
        assertThat(result).isEqualTo(notification);
        verify(repository).save(notification);
    }
    
    @Test
    void givenRepositoryThrowsException_whenSave_thenThrowsInternalException() {
        Notification notification = new Notification();
        when(repository.save(notification)).thenThrow(new RuntimeException("DB error"));
    
        Throwable thrown = catchThrowable(() -> notificationDAO.save(notification));
    
        assertThat(thrown).isInstanceOf(InternalException.class);
        assertThat(((InternalException) thrown).getDetail()).contains("DB error");
    }
    
    @Test
    void givenUserIdAndUserType_whenFindByUserIdAndUserType_thenReturnsNotificationList() {
        Long userId = 1L;
        String userType = "APPLICANT";
        List<Notification> notifications = List.of(new Notification());
        when(repository.findByUserIdAndUserType(userId, userType)).thenReturn(notifications);
    
        List<Notification> result = notificationDAO.findByUserIdAndUserType(userId, userType);
    
        assertThat(result).isEqualTo(notifications);
        verify(repository).findByUserIdAndUserType(userId, userType);
    }
    
    @Test
    void givenRepositoryThrowsException_whenFindByUserIdAndUserType_thenThrowsInternalException() {
        when(repository.findByUserIdAndUserType(any(), any())).thenThrow(new RuntimeException("DB error"));
    
        Throwable thrown = catchThrowable(() -> notificationDAO.findByUserIdAndUserType(1L, "APPLICANT"));
    
        assertThat(thrown).isInstanceOf(InternalException.class);
        assertThat(((InternalException) thrown).getDetail()).contains("DB error");
    }
    
    @Test
    void givenExistingNotificationId_whenFindById_thenReturnsNotification() {
        Notification notification = new Notification();
        when(repository.findById("abc")).thenReturn(Optional.of(notification));
    
        Notification result = notificationDAO.findById("abc");
    
        assertThat(result).isEqualTo(notification);
        verify(repository).findById("abc");
    }
    
    @Test
    void givenNonExistentNotificationId_whenFindById_thenThrowsNotFoundException() {
        when(repository.findById("abc")).thenReturn(Optional.empty());
    
        Throwable thrown = catchThrowable(() -> notificationDAO.findById("abc"));
    
        assertThat(thrown).isInstanceOf(NotFoundException.class);
        assertThat(((NotFoundException) thrown).getDetail()).contains("Notification not found with ID: abc");
    }
    
    @Test
    void givenRepositoryThrowsException_whenFindById_thenThrowsInternalException() {
        when(repository.findById("abc")).thenThrow(new RuntimeException("DB error"));
    
        Throwable thrown = catchThrowable(() -> notificationDAO.findById("abc"));
    
        assertThat(thrown).isInstanceOf(InternalException.class);
        assertThat(((InternalException) thrown).getDetail()).contains("DB error");
    }
    
}