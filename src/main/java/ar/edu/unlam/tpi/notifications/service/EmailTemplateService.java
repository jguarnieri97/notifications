package ar.edu.unlam.tpi.notifications.service;

import java.util.Map;

public interface EmailTemplateService {

    public String generateEmailTemplate(String type) throws Exception;
    
    public String fillTemplate(String template, Map<String,String> templateVariables);

}
