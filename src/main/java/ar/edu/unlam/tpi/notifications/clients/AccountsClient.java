package ar.edu.unlam.tpi.notifications.clients;

import java.util.List;

import ar.edu.unlam.tpi.notifications.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.notifications.dto.response.UserResponse;

public interface AccountsClient {
    UserResponse getAccountById(List<AccountDetailRequest> request);

}
