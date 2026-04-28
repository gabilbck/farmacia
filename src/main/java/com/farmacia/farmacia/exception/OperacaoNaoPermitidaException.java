package com.farmacia.farmacia.exception;

/**
 * Regra de negócio impede a operação (ex.: exclusão com vínculos ativos).
 */
public class OperacaoNaoPermitidaException extends RuntimeException {

    public OperacaoNaoPermitidaException(String message) {
        super(message);
    }
}
