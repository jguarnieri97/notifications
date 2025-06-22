package ar.edu.unlam.tpi.notifications.service.impl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ar.edu.unlam.tpi.notifications.dto.request.EmailCreateRequest;
import ar.edu.unlam.tpi.notifications.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;
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
        }
    }

    private String generateEmailContent(EmailCreateRequest emailCreateRequest){

        try{
            String templateHtml = generateEmailTemplate(emailCreateRequest.getType());
            templateHtml = fillTemplate(templateHtml, emailCreateRequest.getTemplateVariables());
            
            return templateHtml;
        }catch(Exception e){
            log.error("Error al leer el archivo de plantilla de correo electrónico", e);
            return "Error al cargar la plantilla de correo electrónico.";
        }

    }

    private String generateEmailTemplate(String type) throws Exception{

        return switch(type) {
            case "BUDGET" ->
                new String(Files.readAllBytes(Paths.get("src/main/resources/templates/budget-request-email.html")), StandardCharsets.UTF_8);
            case "CONTRACT_EMAIL" ->
                new String(Files.readAllBytes(Paths.get("src/main/resources/templates/work-contract-created-email.html")), StandardCharsets.UTF_8);
            case "CONTRACT_APPLICANT" ->
                new String(Files.readAllBytes(Paths.get("src/main/resources/templates/work-contract-finalized-applicant-email.html")), StandardCharsets.UTF_8);
            case "CONTRACT_SUPPLIER" ->
                new String(Files.readAllBytes(Paths.get("src/main/resources/templates/work-contract-finalized-supplier-email.html")), StandardCharsets.UTF_8);
            default -> "Tipo de correo electrónico no reconocido.";
        };
    }

    private String fillTemplate(String template, Map<String,String> templateVariables) {
        
        for (Map.Entry<String, String> entry : templateVariables.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            template = template.replace(placeholder, entry.getValue());
        }
        return template;
    }
}