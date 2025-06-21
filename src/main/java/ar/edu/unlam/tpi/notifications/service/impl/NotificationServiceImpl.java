package ar.edu.unlam.tpi.notifications.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unlam.tpi.notifications.clients.AccountsClient;
import ar.edu.unlam.tpi.notifications.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.notifications.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.notifications.dto.response.AccountDetailResponse;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationCreateResponse;
import ar.edu.unlam.tpi.notifications.dto.response.NotificationResponse;
import ar.edu.unlam.tpi.notifications.dto.response.UserResponse;
import ar.edu.unlam.tpi.notifications.models.Notification;
import ar.edu.unlam.tpi.notifications.persistence.dao.NotificationDAO;
import ar.edu.unlam.tpi.notifications.service.EmailService;
import ar.edu.unlam.tpi.notifications.service.NotificationService;
import ar.edu.unlam.tpi.notifications.utils.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final NotificationDAO notificationDAO;
    private final EmailService emailService;
    private final AccountsClient accountsClient;

    @Override
    public NotificationCreateResponse saveNewNotification(NotificationCreateRequest request){
        log.info("Creating new notification with request: {}", request);
        Notification notification = instanceNewNotification(request);
        Notification result = notificationDAO.save(notification);

        checkIfWeNeedSendEmail(request);
        return Converter.convertToDto(result, NotificationCreateResponse.class);
    }
    
    private Notification instanceNewNotification(NotificationCreateRequest request) {
        Notification notification  = Converter.convertToEntity(request, Notification.class); 
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDate.now());

        return notification;
    }

    private void checkIfWeNeedSendEmail(NotificationCreateRequest request){
        if(request.getInMail()){
            log.info("Sending email notification to user with id: {}", request.getUserId());
            AccountDetailResponse userInfo = searchUserInformation(request); 
            emailService.sendEmail(userInfo.getEmail(), "prueba");
        } else {
            log.info("Email notification not sent for user with id: {}", request.getUserId());
        }

    }

    private AccountDetailResponse searchUserInformation(NotificationCreateRequest request){
        log.info("Searching user information for user with id: {}", request.getUserId());
        UserResponse userInfo = getUserFromAccountClient(request);
    
        return switch(request.getUserType()) {
            case "SUPPLIER" -> userInfo.getApplicants().get(0);
            case "APPLICANT" -> userInfo.getSuppliers().get(0);
            default -> userInfo.getWorkers().get(0);
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
}
