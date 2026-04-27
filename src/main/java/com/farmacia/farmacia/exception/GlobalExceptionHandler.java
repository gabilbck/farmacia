// exception/GlobalExceptionHandler.java
package com.farmacia.farmacia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 404 - recurso não encontrado
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> handleNaoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponseDTO(404, "Não encontrado", ex.getMessage()));
    }

    // 400 - JSON malformado ou enum inválido no body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponseDTO> handleJsonInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponseDTO(400, "JSON inválido", "Verifique o formato do corpo enviado (objeto x array) e os campos. Enums aceitos — Classificacao: TARJA_PRETA, TARJA_VERMELHA, TARJA_AMARELA, ISENTO_PRESCRICAO, CONTROLADO, GENERICO. FormaFarmaceutica: COMPRIMIDO, CAPSULA, XAROPE, INJETAVEL, POMADA, CREME, GEL, GOTAS, SUPOSITORIO, INALADOR, ADESIVO"));
    }

    // 400 - validação de campos obrigatórios
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> handleValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponseDTO(400, "Requisição inválida", "Há campos em branco, verifique e tente novamente."));
    }

    // 404 - recurso estático inexistente
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErroResponseDTO> handleNoResourceFound(NoResourceFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponseDTO(404, "Não encontrado", "Recurso não encontrado: " + ex.getResourcePath()));
    }

    // 500 - erro inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> handleErroGenerico(Exception ex) {
        log.error("Erro inesperado na API", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErroResponseDTO(500, "Erro interno", "Ocorreu um erro inesperado. Tente novamente mais tarde."));
    }
}