package ar.edu.unlam.tpi.notifications.service;

import java.util.Map;

public interface EmailTemplateService {

    /**
     * Genera una plantilla de correo electrónico basada en el tipo proporcionado.
     *
     * @param type el tipo de plantilla de correo electrónico a generar
     * @return la plantilla de correo electrónico como un String
     * @throws Exception si ocurre un error al generar la plantilla
     */
    public String generateEmailTemplate(String type) throws Exception;
    
    /**
     * Rellena la plantilla proporcionada con las variables dadas.
     *
     * @param template la plantilla de correo electrónico a rellenar
     * @param templateVariables un mapa que contiene las variables para rellenar en la plantilla
     * @return la plantilla de correo electrónico rellenada como un String
     */
    public String fillTemplate(String template, Map<String,String> templateVariables);

}
