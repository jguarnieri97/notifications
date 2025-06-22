package ar.edu.unlam.tpi.notifications.dto.request;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailCreateRequest {
    private String type;
    private String subject;
    private Map<String, String> templateVariables;
}
