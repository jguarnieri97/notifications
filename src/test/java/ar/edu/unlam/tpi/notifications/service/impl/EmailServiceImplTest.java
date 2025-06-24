package ar.edu.unlam.tpi.notifications.service.impl;

import ar.edu.unlam.tpi.notifications.dto.request.EmailCreateRequest;
import ar.edu.unlam.tpi.notifications.exceptions.InternalException;
import ar.edu.unlam.tpi.notifications.service.EmailTemplateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;
    @Mock
    private EmailTemplateService emailTemplateService;
    @Mock
    private MimeMessageHelper mimeMessageHelper;
    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void givenValidRequest_whenSendEmail_thenSendsSuccessfully() throws Exception {
        String to = "test@example.com";
        EmailCreateRequest request = mock(EmailCreateRequest.class);
        when(request.getSubject()).thenReturn("Test Subject");
        when(request.getType()).thenReturn("BUDGET");
        Map<String, String> variables = new HashMap<>();
        variables.put("nombre", "Alan");
        when(request.getTemplateVariables()).thenReturn(variables);
        String templateHtml = "Hola {{nombre}}";
        String filledHtml = "Hola Alan";
        when(emailTemplateService.generateEmailTemplate("BUDGET")).thenReturn(templateHtml);
        when(emailTemplateService.fillTemplate(templateHtml, variables)).thenReturn(filledHtml);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendEmail(to, request);

        verify(mailSender).send(mimeMessage);
    }

    @Test
    void givenMailSenderThrowsException_whenSendEmail_thenThrowsInternalException() throws Exception {
        String to = "fail@example.com";
        EmailCreateRequest request = mock(EmailCreateRequest.class);
        when(request.getSubject()).thenReturn("Test Subject");
        when(request.getType()).thenReturn("BUDGET");
        Map<String, String> variables = new HashMap<>();
        when(request.getTemplateVariables()).thenReturn(variables);
        String templateHtml = "Hola";
        when(emailTemplateService.generateEmailTemplate("BUDGET")).thenReturn(templateHtml);
        when(emailTemplateService.fillTemplate(templateHtml, variables)).thenReturn(templateHtml);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(new RuntimeException("fail")).when(mailSender).send(mimeMessage);

        assertThatThrownBy(() -> emailService.sendEmail(to, request))
        .isInstanceOf(InternalException.class)
        .satisfies(ex -> assertThat(((InternalException) ex).getDetail()).contains("Error al enviar el correo electrónico"));
      }

    @Test
    void givenTemplateError_whenGenerateEmailContent_thenThrowsInternalException() throws Exception {
        EmailCreateRequest request = mock(EmailCreateRequest.class);
        when(request.getType()).thenReturn("BUDGET");
        when(emailTemplateService.generateEmailTemplate("BUDGET")).thenThrow(new RuntimeException("fail"));
        assertThatThrownBy(() -> {
            // Usar reflection para llamar al método privado
            var method = EmailServiceImpl.class.getDeclaredMethod("generateEmailContent", EmailCreateRequest.class);
            method.setAccessible(true);
            method.invoke(emailService, request);
        }).hasRootCauseInstanceOf(InternalException.class);
    }
}
