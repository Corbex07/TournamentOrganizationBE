package nl.fontys.tournamentorganization.Api.Handlers;

import nl.fontys.tournamentorganization.Api.DTOs.ApiMessage;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(
            MethodArgumentNotValidException exception)
    {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        int status = 400;

        return ResponseEntity
                .status(status)
                .body(new ApiMessage(
                        status,
                        "Validation failed.",
                        errors
                ));
    }
<<<<<<< Updated upstream:src/main/java/nl/fontys/tournamentorganization/Api/Handlers/GlobalExceptionHandler.java
=======

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(
            AccessDeniedException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of(
                        "status", 403,
                        "message", "Access denied"
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(
            Exception ex) {

        LOGGER.error("Unexpected exception occurred", ex);

        return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
        ).body(Map.of(
                "status", 500,
                "message", "Internal server error"
        ));


    }

    @ExceptionHandler(TournamentNotFoundException.class)
    public ResponseEntity<?> handleNotFound(
            TournamentNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}