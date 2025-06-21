package ar.edu.unlam.tpi.notifications.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDetailRequest {
    private Long userId;
    private String type;
}
