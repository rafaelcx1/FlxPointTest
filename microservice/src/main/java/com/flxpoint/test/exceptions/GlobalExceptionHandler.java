package com.flxpoint.test.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(400);

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            problemDetail.setProperty(fieldName, error.getDefaultMessage());
        });

        return problemDetail;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail entityNotFoundException(EntityNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail exception(Exception exception) {
        exception.printStackTrace();
        return ProblemDetail.forStatus(HttpStatusCode.valueOf(500));
    }
}
