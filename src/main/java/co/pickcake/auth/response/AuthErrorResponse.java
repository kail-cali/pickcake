package co.pickcake.auth.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class AuthErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation;

    @Builder
    public AuthErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }
    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
