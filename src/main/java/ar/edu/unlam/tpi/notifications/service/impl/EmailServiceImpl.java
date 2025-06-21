package ar.edu.unlam.tpi.notifications.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
    public void sendEmail(String to, String subject){
        try {
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mailSender.send(mimeMessageHelper.getMimeMessage());
            log.info("Correo electrónico enviado a: {}", to);
        } catch (Exception e) {
            log.error("Error al enviar el correo electrónico a: {}", to, e);
        }
    }


}