package ar.edu.unlam.tpi.notifications.integration;

import ar.edu.unlam.tpi.notifications.controller.impl.NotificationControllerImpl;
import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationResponse;
import ar.edu.unlam.tpi.notifications.service.NotificationService;
import ar.edu.unlam.tpi.notifications.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationControllerImpl.class)
public class NotificationIntegration {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenValidRequestBody_whenCreateNotification_thenReturnsGenericResponseOk() throws Exception {
        NotificationCreateRequest request = NotificationCreateRequest.builder().build();
        String json = objectMapper.writeValueAsString(request);
    
        mockMvc.perform(post("/notifications/v1/notification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(Constants.STATUS_OK))
                .andExpect(jsonPath("$.message").value(Constants.SUCCESS_MESSAGE));
    
        ArgumentCaptor<NotificationCreateRequest> captor = ArgumentCaptor.forClass(NotificationCreateRequest.class);
        verify(notificationService).saveNewNotification(captor.capture());
        assertThat(captor.getValue()).isNotNull();
    }
    
    @Test
    void givenValidUserIdAndUserType_whenGetNotificationsByUserIdAndType_thenReturnsNotificationList() throws Exception {
        Long userId = 1L;
        String userType = "APPLICANT";
        NotificationResponse response = new NotificationResponse();
        when(notificationService.getNotificationsByUserIdAndType(userId, userType)).thenReturn(List.of(response));
    
        mockMvc.perform(get("/notifications/v1/notification")
                .param("userId", userId.toString())
                .param("userType", userType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(Constants.STATUS_OK))
                .andExpect(jsonPath("$.message").value(Constants.SUCCESS_MESSAGE))
                .andExpect(jsonPath("$.data").isArray());
    
        verify(notificationService).getNotificationsByUserIdAndType(userId, userType);
    }
    
    @Test
    void givenNotificationId_whenMarkNotificationAsRead_thenReturnsGenericResponseOk() throws Exception {
        String id = "abc";
    
        mockMvc.perform(post("/notifications/v1/notification/read/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(Constants.STATUS_OK))
                .andExpect(jsonPath("$.message").value(Constants.SUCCESS_MESSAGE));
    
        verify(notificationService).markNotificationAsRead(id);
    }
    
}
