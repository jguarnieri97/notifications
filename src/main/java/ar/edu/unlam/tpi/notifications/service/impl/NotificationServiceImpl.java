package ar.edu.unlam.tpi.notifications.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import ar.edu.unlam.tpi.notifications.clients.AccountsClient;
import ar.edu.unlam.tpi.notifications.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.notifications.dto.response.AccountDetailResponse;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationResponse;
import ar.edu.unlam.tpi.notifications.dto.response.UserResponse;
import ar.edu.unlam.tpi.notifications.exceptions.NotFoundException;
import ar.edu.unlam.tpi.notifications.models.Notification;
import ar.edu.unlam.tpi.notifications.persistence.dao.NotificationDAO;
import ar.edu.unlam.tpi.notifications.service.NotificationService;
import ar.edu.unlam.tpi.notifications.utils.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ar.edu.unlam.tpi.notifications.events.EmailSendEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    
    private final NotificationDAO notificationDAO;
    private final AccountsClient accountsClient;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void saveNewNotification(NotificationCreateRequest request){
        log.info("Creating new notification with request: {}", request);
        
        Notification notification = instanceNewNotification(request);
        notificationDAO.save(notification);
        log.info("Saving new notification");

        checkIfWeNeedSendEmail(request);
    }
    
    private Notification instanceNewNotification(NotificationCreateRequest request) {
        Notification notification  = Converter.convertToEntity(request, Notification.class); 
        notification.setId(null);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDate.now());

        return notification;
    }

    private void checkIfWeNeedSendEmail(NotificationCreateRequest request){
        if (request.getInMail()) {
            log.info("Sending email notification to user with id: {}", request.getUserId());
            AccountDetailResponse userInfo = searchUserInformation(request);

            eventPublisher.publishEvent(EmailSendEvent.of(this, userInfo.getEmail(), request.getEmailCreateRequest()));
        } else {
            log.info("Email notification not sent for user with id: {}", request.getUserId());
        }
    }

    private AccountDetailResponse searchUserInformation(NotificationCreateRequest request){
        log.info("Searching user information for user with id: {}", request.getUserId());
        UserResponse userInfo = getUserFromAccountClient(request);
    
        return switch(request.getUserType()) {
            case "SUPPLIER" -> userInfo.getSuppliers().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("No supplier found for user ID: " + request.getUserId()));
            
            case "APPLICANT" -> userInfo.getApplicants().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("No applicant found for user ID: " + request.getUserId()));
            
            default -> userInfo.getWorkers().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("No worker found for user ID: " + request.getUserId()));
        };
    }

    private UserResponse getUserFromAccountClient(NotificationCreateRequest request){
        log.info("Requesting user information from accounts client for user with id: {}", request.getUserId());
        List<AccountDetailRequest> requestClient = List.of(new AccountDetailRequest(request.getUserId(), request.getUserType()));
        UserResponse userResponse = accountsClient.getAccountById(requestClient);
        return userResponse;
    }


    @Override
    public List<NotificationResponse> getNotificationsByUserIdAndType(Long userId, String userType) {
        log.info("Retrieving notifications for user ID: {} and user type: {}", userId, userType);
        List<Notification> notifications = notificationDAO.findByUserIdAndUserType(userId, userType);
        return Converter.convertToDtoList(notifications, NotificationResponse.class);
    }

    @Override
    public void markNotificationAsRead(String id) {
        log.info("Marking notification with ID: {} as read", id);
        Notification notification = notificationDAO.findById(id);
        notification.setIsRead(true);
        notificationDAO.save(notification);
    }
}
