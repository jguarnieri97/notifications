package ar.edu.unlam.tpi.notifications.utils;

import java.util.Map;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConstantsTemplates {
    public static final String BUDGET = "src/main/resources/templates/budget-request-email.html";
    public static final String CONTRACT_EMAIL="src/main/resources/templates/work-contract-created-email.html";
    public static final String CONTRACT_APPLICANT="src/main/resources/templates/work-contract-finalized-applicant-email.html";
    public static final String CONTRACT_SUPPLIER="src/main/resources/templates/work-contract-finalized-supplier-email.html";

    public static final Map<String, String> TEMPLATE_PATHS = Map.of(
        "BUDGET", BUDGET,
        "CONTRACT_EMAIL", CONTRACT_EMAIL,
        "CONTRACT_APPLICANT", CONTRACT_APPLICANT,
        "CONTRACT_SUPPLIER", CONTRACT_SUPPLIER
    );
}
