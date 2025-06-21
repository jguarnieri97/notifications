package ar.edu.unlam.tpi.notifications.clients.error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import ar.edu.unlam.tpi.notifications.dto.response.ErrorResponse;
import ar.edu.unlam.tpi.notifications.exceptions.AccountsClientException;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ErrorHandler {

    public Mono<Throwable> handle4xxError(ErrorResponse error) {
        log.error("Error del cliente externo: {}", error);
        return Mono.error(new AccountsClientException(error));
    }

    public Mono<Throwable> handle5xxError(ErrorResponse error) {
        log.error("Error del servidor externo: {}", error);
        return Mono.error(new AccountsClientException(error));
    }

    public Mono<Throwable> onClientError(Throwable e) {
        log.error("Error al ejecutar el request: {}", e.getMessage());
        throw new AccountsClientException(e.getMessage());
    }

}

