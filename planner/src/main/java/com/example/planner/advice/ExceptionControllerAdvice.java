package com.example.planner.advice;

import com.example.planner.dto.ExceptionDTO;
import com.example.planner.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionDTO handle(ForbiddenException e) {
        e.printStackTrace();
        return new ExceptionDTO("forbidden exception");
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO handle(AppointmentNotFoundException e) {
        e.printStackTrace();
        return new ExceptionDTO("appointment not found");
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handle(InvalidStationException e) {
        e.printStackTrace();
        return new ExceptionDTO("invalid station");
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handle(InvalidUserException e) {
        e.printStackTrace();
        return new ExceptionDTO("invalid user");
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handle(TimeAlreadyTakenException e) {
        e.printStackTrace();
        return new ExceptionDTO("time already taken");
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDTO handle(Exception e) {
        e.printStackTrace();
        return new ExceptionDTO("global internal error");
    }
}
