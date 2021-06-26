package com.bol.mancala.exception;

import lombok.Getter;

@Getter
public class IntMancalaBaseException extends RuntimeException {
    private final String customMessage;

    public IntMancalaBaseException(String customMessage) {
        super(IntMancalaBaseException.class.getSuperclass().getCanonicalName());
        this.customMessage = customMessage;
    }

    
}
