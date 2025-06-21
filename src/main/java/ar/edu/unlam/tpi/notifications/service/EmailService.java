package ar.edu.unlam.tpi.notifications.service;

public interface EmailService {
    /**
     * Envia un correo electrónico.
     *
     * @param to destinatario del correo electrónico
     * @param subject asunto del correo electrónico
     * @param content contenido del correo electrónico
     */
    void sendEmail(String to, String subject);    
}
