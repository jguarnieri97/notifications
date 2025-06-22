package ar.edu.unlam.tpi.notifications.events;

import org.springframework.context.ApplicationEvent;
import ar.edu.unlam.tpi.notifications.dto.request.EmailCreateRequest;
import lombok.Getter;

@Getter
public class EmailSendEvent extends ApplicationEvent{
    private final EmailCreateRequest emailCreateRequest;
    private final String to;

    public EmailSendEvent(Object source, String to, EmailCreateRequest emailCreateRequest) {
        super(source);
        this.to = to;
        this.emailCreateRequest = emailCreateRequest;
    }

    public static EmailSendEvent of(Object source, String to, EmailCreateRequest emailCreateRequest) {
        return new EmailSendEvent(source, to, emailCreateRequest);
    }
}
