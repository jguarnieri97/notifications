package ar.edu.unlam.tpi.notifications.service;

import ar.edu.unlam.tpi.notifications.dto.request.EmailCreateRequest;

public interface EmailService {
    /**
     * Envia un correo electrónico.
     *
     * @param to destinatario del correo electrónico
     * @param emailCreateRequest objeto que contiene la información del correo electrónico a enviar   
     */
    public void sendEmail(String to, EmailCreateRequest emailCreateRequest);
}
