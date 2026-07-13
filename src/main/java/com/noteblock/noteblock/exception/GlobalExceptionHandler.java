package com.noteblock.noteblock.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.noteblock.noteblock.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErrorResponseDTO> handleCredenciaisInvalidas(CredenciaisInvalidasException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Credenciais inválidas", ex.getMessage());
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailJaCadastrado(EmailJaCadastradoException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Email já cadastrado", ex.getMessage());
    }

    @ExceptionHandler(NotaNaoEncontradaException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotaNaoEncontrada(NotaNaoEncontradaException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Nota não encontrada", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> erros.put(e.getField(), e.getDefaultMessage()));

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Erro de validação", "Dados inválidos", erros);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenerico(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", "Erro inesperado");
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(
            HttpStatus status,
            String error,
            String message) {
        return buildErrorResponse(status, error, message, null);
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(
            HttpStatus status,
            String error,
            String message,
            Map<String, String> fieldErrors) {
        ErrorResponseDTO response = new ErrorResponseDTO(
                status.value(),
                error,
                message,
                LocalDateTime.now(),
                fieldErrors);
        return ResponseEntity.status(status).body(response);
    }

}