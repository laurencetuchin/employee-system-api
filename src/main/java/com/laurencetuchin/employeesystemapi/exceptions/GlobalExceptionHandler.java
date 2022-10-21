package com.laurencetuchin.employeesystemapi.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<Object> handleEmployeeNotFoundException(final EmployeeNotFoundException exception){
        return new ResponseEntity<Object>();
    }
}
