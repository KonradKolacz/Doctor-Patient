package com.example.zajecia30.controller;

import com.example.zajecia30.dto.ExceptionDto;
import com.example.zajecia30.exception.BusyTermException;
import com.example.zajecia30.exception.FutureDateException;
import com.example.zajecia30.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleObjectNotFoundException(ObjectNotFoundException e){
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FutureDateException.class)
    public ResponseEntity<ExceptionDto> handleFutureDateException(FutureDateException e){
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusyTermException.class)
    public ResponseEntity<ExceptionDto> handleBusyTermException(BusyTermException e){
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ExceptionDto>> handleValidationErrors(ConstraintViolationException e) {
        final List<ExceptionDto> errors = e.getConstraintViolations()
                .stream()
                .map(fieldError -> new ExceptionDto(fieldError.getPropertyPath().toString() + " : " + fieldError.getMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
