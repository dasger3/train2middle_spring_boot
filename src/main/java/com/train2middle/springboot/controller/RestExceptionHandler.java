package com.train2middle.springboot.controller;

import com.train2middle.springboot.SpringbootApplication;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(SpringbootApplication.class);

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> exception(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();

        logger.error(bodyOfResponse);

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = SecurityException.class)
    protected ResponseEntity<Object> exceptionSecurity(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();

        logger.error(bodyOfResponse);

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = ObjectNotFoundException.class)
    protected ResponseEntity<Object> exceptionNotFound(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();

        logger.error(bodyOfResponse);

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
