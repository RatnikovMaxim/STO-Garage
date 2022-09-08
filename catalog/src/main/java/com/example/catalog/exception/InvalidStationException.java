package com.example.catalog.exception;

public class InvalidStationException  extends Exception {
    public InvalidStationException() {
    }

    public InvalidStationException(String message) {
        super(message);
    }

    public InvalidStationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStationException(Throwable cause) {
        super(cause);
    }

    public InvalidStationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
