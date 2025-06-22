package ar.edu.unlam.tpi.notifications.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmailTemplateServiceImplTest {

    @InjectMocks
    private EmailTemplateServiceImpl service;

    @Test
    void givenTemplateWithVariables_whenFillTemplate_thenReplacesPlaceholders() {
        String template = "Hola {{nombre}}, tu código es {{codigo}}.";
        Map<String, String> variables = new HashMap<>();
        variables.put("nombre", "Alan");
        variables.put("codigo", "1234");
        String result = service.fillTemplate(template, variables);
        assertThat(result).isEqualTo("Hola Alan, tu código es 1234.");
    }

    @Test
    void givenTemplateWithMissingVariable_whenFillTemplate_thenLeavesPlaceholder() {
        String template = "Hola {{nombre}}, tu código es {{codigo}}.";
        Map<String, String> variables = new HashMap<>();
        variables.put("nombre", "Alan");
        String result = service.fillTemplate(template, variables);
        assertThat(result).isEqualTo("Hola Alan, tu código es {{codigo}}.");
    }

}
