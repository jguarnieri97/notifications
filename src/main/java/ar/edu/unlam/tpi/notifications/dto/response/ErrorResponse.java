package ar.edu.unlam.tpi.notifications.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private Integer code;
    private String message;
    private String detail;
}
