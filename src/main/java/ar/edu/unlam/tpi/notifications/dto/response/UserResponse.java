package ar.edu.unlam.tpi.notifications.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private List<AccountDetailResponse> applicants;
    private List<AccountDetailResponse> suppliers;
    private List<AccountDetailResponse> workers;
}
