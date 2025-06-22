package ar.edu.unlam.tpi.notifications.service.impl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ar.edu.unlam.tpi.notifications.dto.request.EmailCreateRequest;
import ar.edu.unlam.tpi.notifications.exceptions.InternalException;
import ar.edu.unlam.tpi.notifications.service.EmailService;
import ar.edu.unlam.tpi.notifications.service.EmailTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;
    private final EmailTemplateService emailTemplateService;

    @Qualifier("mimeMessageHelper")
    private final MimeMessageHelper mimeMessageHelper;

    @Override
    public void sendEmail(String to, EmailCreateRequest emailCreateRequest) {
        try {
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(emailCreateRequest.getSubject());
            String content = generateEmailContent(emailCreateRequest);
            mimeMessageHelper.setText(content, true);
            mailSender.send(mimeMessageHelper.getMimeMessage());
            log.info("Correo electrónico enviado a: {}", to);
        } catch (Exception e) {
            log.error("Error al enviar el correo electrónico a: {}", to, e);
            throw new InternalException("Error al enviar el correo electrónico");
        }
    }

    private String generateEmailContent(EmailCreateRequest emailCreateRequest){

        try{
            String templateHtml = emailTemplateService.generateEmailTemplate(emailCreateRequest.getType());
            templateHtml = emailTemplateService.fillTemplate(templateHtml, emailCreateRequest.getTemplateVariables());
            
            return templateHtml;
        }catch(Exception e){
            log.error("Error al leer el archivo de plantilla de correo electrónico", e);
            throw new InternalException("Error al leer el archivo de plantilla de correo electrónico");
        }

    }
}