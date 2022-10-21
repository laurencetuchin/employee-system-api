package com.laurencetuchin.employeesystemapi.exceptions;

import com.laurencetuchin.employeesystemapi.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEmployeeNotFoundException(final EmployeeNotFoundException exception) {
        return new ResponseEntity<ErrorDTO>(
                ErrorDTO.builder()
                        .withTitle("Invalid Employee")
                        .withDetail(exception.getMessage())
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withErrorType(EmployeeNotFoundException.class.getSimpleName())
                        .withErrorCode("Custom Error Code XXX").build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleOtherException(final Exception exception) {

        return new ResponseEntity<>(
                ErrorDTO.builder()
                        .withDetail(exception.getMessage())
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withErrorType(exception.getClass().getSimpleName())
                        .withErrorCode("Other Error Code XXX")
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
