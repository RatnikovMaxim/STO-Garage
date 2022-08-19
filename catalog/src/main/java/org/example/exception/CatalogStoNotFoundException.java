package org.example.exception;

public class CatalogStoNotFoundException extends RuntimeException {
    public CatalogStoNotFoundException() {
    }

    public CatalogStoNotFoundException(String message) {
        super(message);
    }

    public CatalogStoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CatalogStoNotFoundException(Throwable cause) {
        super(cause);
    }

    public CatalogStoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
