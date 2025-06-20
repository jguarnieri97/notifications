package ar.edu.unlam.tpi.notifications.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationCreateResponseDto {
    private Long id;
    private LocalDate createdAt;
}
