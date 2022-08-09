package com.mz.movierest.controllers;

import com.mz.movierest.exceptions.ItemNotFoundException;
import com.mz.movierest.models.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleException(MethodArgumentNotValidException exception) {
        var errorMessages = exception.getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage())
                .toList();
        log.error("errors: {}", errorMessages);
        return ResponseEntity.badRequest().body(
                new Error(errorMessages, LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase())
        );
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Error> handleException(ItemNotFoundException exception) {
        log.error("error: {}", exception.getMessage());
        var body = new Error(List.of(exception.getMessage()), LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }


}
