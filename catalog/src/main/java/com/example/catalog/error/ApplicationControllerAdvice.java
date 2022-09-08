package com.example.catalog.error;


import com.example.catalog.dto.InvalidRequestExceptionDTO;
import com.example.catalog.exception.StationNotFoundException;
import com.example.id.exception.ForbiddenException;
import com.example.catalog.exception.ServiceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handle(final MethodArgumentNotValidException e) {
        return new ErrorResponseDTO(ErrorResponseDTO.ARGUMENT_NOT_VALID);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDTO handle(final ForbiddenException e) {
        return new ErrorResponseDTO(ErrorResponseDTO.EXECUTE_ACCESS_FORBIDDEN);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handle(final ServiceNotFoundException e) {
        return new ErrorResponseDTO(ErrorResponseDTO.THE_SERVER_CANNOT_FIND_THE_DATA);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handle(final StationNotFoundException e) {
        return new ErrorResponseDTO(ErrorResponseDTO.THE_SERVER_CANNOT_FIND_THE_DATA);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handle(final InvalidRequestExceptionDTO e) {
        return new ErrorResponseDTO(ErrorResponseDTO.NOTHING_WAS_FOUND_FOR_YOUR_QUERY);
    }


}
