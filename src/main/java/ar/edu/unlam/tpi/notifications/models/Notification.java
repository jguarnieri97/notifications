package ar.edu.unlam.tpi.notifications.models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    private Long userId;
    private Boolean inMail;
    private String content;
    private Boolean isRead;
    private LocalDate createdAt;
}
