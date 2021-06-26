package com.bol.mancala.handler;

import com.bol.mancala.exception.BadRequestException;
import com.bol.mancala.exception.IntMancalaBaseException;
import com.bol.mancala.exception.IntServiceException;
import com.bol.mancala.model.ApiErrorMessageBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class BaseRestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({IntMancalaBaseException.class})
    public ResponseEntity<Object> handleIntegrationsBaseException(IntMancalaBaseException ex) {
        return this.handleBadRequestExceptions(ex);
    }

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequestExceptions(IntMancalaBaseException ex) {

        ApiErrorMessageBody apiErrorMessageBody = createApiErrorBody(ex.getCustomMessage());

        return new ResponseEntity<>(apiErrorMessageBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IntServiceException.class})
    protected ResponseEntity<Object> handleIntServiceException(IntMancalaBaseException ex) {

        ApiErrorMessageBody apiErrorMessageBody = createApiErrorBody(ex.getCustomMessage());

        return new ResponseEntity<>(apiErrorMessageBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ApiErrorMessageBody createApiErrorBody(String errorMessage) {
        return new ApiErrorMessageBody(errorMessage);
    }

}
