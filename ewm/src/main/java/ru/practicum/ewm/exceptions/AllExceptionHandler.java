package ru.practicum.ewm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AllExceptionHandler {
    @ExceptionHandler(DataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, HttpStatus> handleIllegalArgumentException(DataException ex) {
        return Map.of("Conflict", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, HttpStatus> handleRuntimeException(NotFoundException ex) {
        return Map.of("NotFound", HttpStatus.NOT_FOUND);
    }
}