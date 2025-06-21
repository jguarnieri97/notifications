package ar.edu.unlam.tpi.notifications.configuration;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
@Slf4j
public class EmailConfig {

    @Bean(name="content")
    public String generateTemplate(){

        try{
            String template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/email.html")), StandardCharsets.UTF_8);

            String detalle = "Es una prueba";
            String htmlContent = template.replace("{{detalle}}", detalle);
    
            return htmlContent;
        }catch(Exception e){
            log.error("Error al leer el archivo de plantilla de correo electrónico", e);
            return "Error al cargar la plantilla de correo electrónico.";
        }

    }

    @Bean(name="mimeMessage")
    public MimeMessage generateMimeMessage(JavaMailSender mailSender){
        return mailSender.createMimeMessage();
    }

    @Bean(name="mimeMessageHelper")
    public MimeMessageHelper generateMimeMessageHelper(@Qualifier("content") String content, @Qualifier("mimeMessage") MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setFrom("support@nexwork.com", "Nexwork Support");
        mimeMessageHelper.setText(content,true);

        return mimeMessageHelper;
    }

}
