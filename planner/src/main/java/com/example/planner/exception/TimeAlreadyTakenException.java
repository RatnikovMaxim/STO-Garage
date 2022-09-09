package com.example.planner.exception;

public class TimeAlreadyTakenException extends Exception {
    public TimeAlreadyTakenException() {
    }

    public TimeAlreadyTakenException(String message) {
        super(message);
    }

    public TimeAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeAlreadyTakenException(Throwable cause) {
        super(cause);
    }

    public TimeAlreadyTakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
