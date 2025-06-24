package ar.edu.unlam.tpi.notifications.configuration;

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
    
    @Bean(name="mimeMessage")
    public MimeMessage generateMimeMessage(JavaMailSender mailSender){
        return mailSender.createMimeMessage();
    }

    @Bean(name="mimeMessageHelper")
    public MimeMessageHelper generateMimeMessageHelper(@Qualifier("mimeMessage") MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("support@nexwork.com", "Nexwork Support");
        return mimeMessageHelper;
    }

}
