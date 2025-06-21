package org.example.msvccuenta.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, String> error = new HashMap<>();
        String fieldName = ex.getName();
        String requiredType = ex.getRequiredType().getSimpleName();

        String message = String.format("El valor '%s' no es válido para el campo '%s'. Se esperaba un valor de tipo '%s'.",
                ex.getValue(), fieldName, requiredType);

        error.put("error", message);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}