package ar.edu.unlam.tpi.notifications.controller;

import ar.edu.unlam.tpi.notifications.controller.impl.NotificationControllerImpl;
import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.notifications.dto.response.GenericResponse;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationResponse;
import ar.edu.unlam.tpi.notifications.service.NotificationService;
import ar.edu.unlam.tpi.notifications.utils.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerImplTest {
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationControllerImpl controller;

    @Test
    void givenValidRequest_whenCreateNotification_thenCallsServiceAndReturnsGenericSuccessResponse() {
        NotificationCreateRequest request = NotificationCreateRequest.builder().build();
    
        GenericResponse<Void> response = controller.createNotification(request);
    
        verify(notificationService).saveNewNotification(request);
        assertThat(response.getCode()).isEqualTo(Constants.STATUS_OK);
        assertThat(response.getMessage()).isEqualTo(Constants.SUCCESS_MESSAGE);
        assertThat(response.getData()).isNull();
    }
    
    @Test
    void givenValidUserIdAndUserType_whenGetNotificationsByUserIdAndType_thenCallsServiceAndReturnsResponseWithData() {
        Long userId = 1L;
        String userType = "APPLICANT";
        NotificationResponse notification = new NotificationResponse();
        when(notificationService.getNotificationsByUserIdAndType(userId, userType)).thenReturn(List.of(notification));
    
        GenericResponse<List<NotificationResponse>> response = controller.getNotificationsByUserIdAndType(userId, userType);
    
        verify(notificationService).getNotificationsByUserIdAndType(userId, userType);
        assertThat(response.getCode()).isEqualTo(Constants.STATUS_OK);
        assertThat(response.getMessage()).isEqualTo(Constants.SUCCESS_MESSAGE);
        assertThat(response.getData()).containsExactly(notification);
    }
    
    @Test
    void givenNotificationId_whenMarkNotificationAsRead_thenCallsServiceAndReturnsGenericSuccessResponse() {
        String id = "abc";
    
        GenericResponse<Void> response = controller.markNotificationAsRead(id);
    
        verify(notificationService).markNotificationAsRead(id);
        assertThat(response.getCode()).isEqualTo(Constants.STATUS_OK);
        assertThat(response.getMessage()).isEqualTo(Constants.SUCCESS_MESSAGE);
        assertThat(response.getData()).isNull();
    }
    
}
