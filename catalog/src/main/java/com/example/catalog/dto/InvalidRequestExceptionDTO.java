package com.example.catalog.dto;

public class InvalidRequestExceptionDTO extends Exception {
    public InvalidRequestExceptionDTO() {
    }

    public InvalidRequestExceptionDTO(String message) {
        super(message);
    }

    public InvalidRequestExceptionDTO(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequestExceptionDTO(Throwable cause) {
        super(cause);
    }

    public InvalidRequestExceptionDTO(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
