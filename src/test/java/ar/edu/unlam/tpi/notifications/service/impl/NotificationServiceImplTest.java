package ar.edu.unlam.tpi.notifications.service.impl;

import ar.edu.unlam.tpi.notifications.clients.AccountsClient;
import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.notifications.dto.response.AccountDetailResponse;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationResponse;
import ar.edu.unlam.tpi.notifications.dto.response.UserResponse;
import ar.edu.unlam.tpi.notifications.events.EmailSendEvent;
import ar.edu.unlam.tpi.notifications.exceptions.NotFoundException;
import ar.edu.unlam.tpi.notifications.models.Notification;
import ar.edu.unlam.tpi.notifications.persistence.dao.NotificationDAO;
import ar.edu.unlam.tpi.notifications.utils.Converter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {

    @Mock
    private NotificationDAO notificationDAO;
    @Mock
    private AccountsClient accountsClient;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private NotificationServiceImpl notificationService;


    @Test
    void givenInMailTrue_whenSaveNewNotification_thenPublishesEventAndSavesNotification() {
        NotificationCreateRequest request = mock(NotificationCreateRequest.class);
        when(request.getInMail()).thenReturn(true);
        when(request.getUserId()).thenReturn(1L);
        when(request.getUserType()).thenReturn("APPLICANT");
        when(request.getEmailCreateRequest()).thenReturn(null);
    
        Notification notification = new Notification();
        UserResponse userResponse = mock(UserResponse.class);
        AccountDetailResponse accountDetail = mock(AccountDetailResponse.class);
        when(accountsClient.getAccountById(any())).thenReturn(userResponse);
        when(userResponse.getApplicants()).thenReturn(List.of(accountDetail));
        when(notificationDAO.save(any())).thenReturn(notification);
    
        notificationService.saveNewNotification(request);
    
        verify(notificationDAO).save(any(Notification.class));
        verify(eventPublisher).publishEvent(any(EmailSendEvent.class));
    }
    
    @Test
    void givenInMailFalse_whenSaveNewNotification_thenDoesNotPublishEvent() {
        NotificationCreateRequest request = mock(NotificationCreateRequest.class);
        when(request.getInMail()).thenReturn(false);
        Notification notification = new Notification();
        when(notificationDAO.save(any())).thenReturn(notification);
    
        notificationService.saveNewNotification(request);
    
        verify(notificationDAO).save(any(Notification.class));
        verify(eventPublisher, never()).publishEvent(any());
    }
    
    @Test
    void givenValidUserIdAndUserType_whenGetNotificationsByUserIdAndType_thenReturnsDtoList() {
        Long userId = 1L;
        String userType = "APPLICANT";
        Notification notification = new Notification();
        List<Notification> notifications = List.of(notification);
        when(notificationDAO.findByUserIdAndUserType(userId, userType)).thenReturn(notifications);
        NotificationResponse response = new NotificationResponse();
    
        try (MockedStatic<Converter> converterMock = mockStatic(Converter.class)) {
            converterMock.when(() -> Converter.convertToDtoList(notifications, NotificationResponse.class))
                .thenReturn(List.of(response));
    
            List<NotificationResponse> result = notificationService.getNotificationsByUserIdAndType(userId, userType);
    
            assertThat(result).containsExactly(response);
        }
    }
    
    @Test
    void givenUnreadNotificationId_whenMarkNotificationAsRead_thenMarksAndSavesNotification() {
        String id = "abc";
        Notification notification = new Notification();
        notification.setIsRead(false);
        when(notificationDAO.findById(id)).thenReturn(notification);
        when(notificationDAO.save(any())).thenReturn(notification);
    
        notificationService.markNotificationAsRead(id);
    
        assertThat(notification.getIsRead()).isTrue();
        verify(notificationDAO).save(notification);
    }
    
    @Test
    void givenNoApplicantInUserResponse_whenSearchUserInformation_thenThrowsNotFoundException() {
        NotificationCreateRequest request = mock(NotificationCreateRequest.class);
        when(request.getUserId()).thenReturn(1L);
        when(request.getUserType()).thenReturn("APPLICANT");
    
        UserResponse userResponse = mock(UserResponse.class);
        when(accountsClient.getAccountById(any())).thenReturn(userResponse);
        when(userResponse.getApplicants()).thenReturn(List.of());
    
        assertThatThrownBy(() -> {
            var method = NotificationServiceImpl.class
                .getDeclaredMethod("searchUserInformation", NotificationCreateRequest.class);
            method.setAccessible(true);
            method.invoke(notificationService, request);
        }).hasRootCauseInstanceOf(NotFoundException.class);
    }
    
} 