package ar.edu.unlam.tpi.notifications.service.impl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.stereotype.Service;

import ar.edu.unlam.tpi.notifications.exceptions.NotFoundException;
import ar.edu.unlam.tpi.notifications.service.EmailTemplateService;
import ar.edu.unlam.tpi.notifications.utils.ConstantsTemplates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Override
    public String generateEmailTemplate(String type) throws Exception {
        String path = ConstantsTemplates.TEMPLATE_PATHS.get(type);
        if (path == null) {
            throw new NotFoundException("Template not found for type: " + type);
        }
        return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
    }

    @Override
    public String fillTemplate(String template, Map<String,String> templateVariables) {
        
        for (Map.Entry<String, String> entry : templateVariables.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            String replacement = entry.getValue() != null ? entry.getValue() : "";
            template = template.replace(placeholder, replacement);
        }
        return template;
    }
    
}
