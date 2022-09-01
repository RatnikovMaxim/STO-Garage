package com.example.bonus.exception;

public class BonusNotFoundException extends RuntimeException{
    public BonusNotFoundException() {
    }

    public BonusNotFoundException(String message) {
        super(message);
    }

    public BonusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BonusNotFoundException(Throwable cause) {
        super(cause);
    }

    public BonusNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
