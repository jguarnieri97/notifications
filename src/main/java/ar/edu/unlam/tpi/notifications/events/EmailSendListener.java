package ar.edu.unlam.tpi.notifications.events;

import org.springframework.context.event.EventListener;

import ar.edu.unlam.tpi.notifications.service.EmailService;
import ar.edu.unlam.tpi.notifications.utils.annotations.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EventHandler
@RequiredArgsConstructor
@Slf4j
public class EmailSendListener {
    private final EmailService emailService;

    @EventListener
    public void handleEmailToSend(EmailSendEvent event) {
        emailService.sendEmail(event.getTo(), event.getEmailCreateRequest());
        log.info("Email send to {}", event.getTo());
    }
}
