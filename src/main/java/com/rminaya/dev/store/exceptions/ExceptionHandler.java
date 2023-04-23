package com.rminaya.dev.store.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    // Controlador de las excepciones lanzadas con "DevStoreExceptions"
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {DevStoreExceptions.class})
    protected ResponseEntity<Object> handleConflict(DevStoreExceptions ex, WebRequest request) {

        Map<String, Object> bodyOfResponse = new HashMap<>();
        bodyOfResponse.put("mensaje", ex.getMensaje());

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), ex.getHttpStatus(), request);
    }
    // 1° Opción para controlar las validaciones de los request
    @Override
    @ResponseStatus
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> bodyOfResponse = new HashMap<>();

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                .toList();

        bodyOfResponse.put("errors", errors);
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }

    // 2° Opción para controlar las validaciones de los request
    /*
    // Necesita añadir "BindingResult bindingResult" en el método del controller
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {ConstraintViolationException.class})
    protected Map<String, Object> handleInvalidArgument(ConstraintViolationException ex) {
        Map<String, Object> bodyOfResponse = new HashMap<>();
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(constraintViolation -> "El campo '" + constraintViolation.getPropertyPath() + "' " + constraintViolation.getMessage())
                .toList();

        bodyOfResponse.put("errors", errors);

        return bodyOfResponse;
    }
    */
}
