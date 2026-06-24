package Minimarket.pesaje.exception;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> validationErrors; // Para guardar los errores de @Valid
    private String path;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now(); // La fecha se asigna sola al crear el error
    }
}