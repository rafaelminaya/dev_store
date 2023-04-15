package com.rminaya.dev.store.exceptions;

import org.springframework.http.HttpStatus;

public class DevStoreExceptions extends RuntimeException {
    private String mensaje;
    private HttpStatus httpStatus;

    public DevStoreExceptions(String message, HttpStatus httpStatus) {
        super(message);
        this.mensaje = message;
        this.httpStatus = httpStatus;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
