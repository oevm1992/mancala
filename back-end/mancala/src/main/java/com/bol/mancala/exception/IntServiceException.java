package com.bol.mancala.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class IntServiceException extends IntMancalaBaseException {

    public IntServiceException(String errorMsg) {
        super(errorMsg);
    }
}
