package ar.edu.unlam.tpi.notifications.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {
    private String id;
    private Long userId;
    private String userType;
    private String content;
    private Boolean isRead;
}
