package org.example.msvccuenta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

/**
 * Manejador de excepciones global para toda la aplicación.
 * Captura las excepciones lanzadas por los controladores y las transforma
 * en respuestas HTTP consistentes y claras.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones cuando no se encuentra una cuenta.
     * Devuelve un estado 404 Not Found.
     * @param ex La excepción lanzada.
     * @return Un ResponseEntity con el mensaje de error y el código de estado HTTP.
     */
    @ExceptionHandler(CuentaNoEncontradaException.class)
    public ResponseEntity<Map<String, String>> handleCuentaNoEncontrada(CuentaNoEncontradaException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja las excepciones de argumentos inválidos (ej. un monto de recarga negativo)
     * o de estado ilegal (ej. intentar una operación en una cuenta anulada).
     * Devuelve un estado 400 Bad Request.
     * @param ex La excepción lanzada.
     * @return Un ResponseEntity con el mensaje de error y el código de estado HTTP.
     */
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, String>> handleInvalidRequests(RuntimeException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja la excepción específica de intentar recargar una cuenta PREMIUM,
     * así como cualquier otro error de runtime no capturado específicamente.
     * @param ex La excepción lanzada.
     * @return Un ResponseEntity con el mensaje de error y el código de estado HTTP.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleGenericRuntimeException(RuntimeException ex) {
        // Caso específico para la regla de negocio de cuentas PREMIUM
        if (ex.getMessage() != null && ex.getMessage().contains("cuentas PREMIUM")) {
            return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.CONFLICT);
        }

        // Para cualquier otro error inesperado, lo registramos y devolvemos un error 500.
        // Es crucial para la depuración ver la traza completa en la consola.
        ex.printStackTrace();
        return new ResponseEntity<>(Map.of("error", "Ocurrió un error interno inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja las excepciones cuando un parámetro en la URL no tiene el tipo correcto
     * (ej. se pasa "abc" como ID en lugar de un número).
     * Devuelve un estado 400 Bad Request.
     * @param ex La excepción lanzada.
     * @return Un ResponseEntity con un mensaje de error claro y el código de estado HTTP.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        // Asegurarnos de que getRequiredType() no es nulo antes de llamar a getSimpleName()
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido";
        String message = String.format("El valor '%s' en el parámetro '%s' no es válido. Se esperaba un valor de tipo '%s'.",
                ex.getValue(), ex.getName(), requiredType);
        return new ResponseEntity<>(Map.of("error", message), HttpStatus.BAD_REQUEST);
    }
}