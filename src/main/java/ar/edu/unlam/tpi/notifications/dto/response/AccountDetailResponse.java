package ar.edu.unlam.tpi.notifications.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDetailResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String cuit;
}
