// exception/RecursoNaoEncontradoException.java
package com.farmacia.farmacia.exception;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}