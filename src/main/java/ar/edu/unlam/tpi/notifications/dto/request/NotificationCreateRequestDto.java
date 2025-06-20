package ar.edu.unlam.tpi.notifications.dto.request;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationCreateRequestDto {
    private Long userId;
    private Boolean inMail;
    private String content;
    private Boolean isRead;
    private LocalDate createdAt;
}
