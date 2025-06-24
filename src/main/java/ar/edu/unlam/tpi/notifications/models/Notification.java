package ar.edu.unlam.tpi.notifications.models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {
    
    @Id
    private String id;
    @Field(name="user_id")
    private Long userId;
    @Field(name="user_type")
    private String userType;
    @Field(name="in_mail")
    private Boolean inMail;
    private String content;
    @Field(name="in_read")
    private Boolean isRead;
    @Field(name="created_at")
    private LocalDate createdAt;
}
