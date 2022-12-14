package com.laurencetuchin.employeesystemapi.exceptions;

import com.laurencetuchin.employeesystemapi.dto.ErrorDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

//    @ExceptionHandler(HttpClientErrorException.NotFound.class)
//    public ResponseEntity<ErrorDTO> handleEmployeeNotFoundException(final HttpClientErrorException.NotFound exception) {
//        return new ResponseEntity<ErrorDTO>(
//                ErrorDTO.builder()
//                        .withTitle("Invalid Employee")
//                        .withDetail(exception.getMessage())
//                        .withStatus(HttpStatus.NOT_FOUND.value())
//                        .withErrorType(HttpClientErrorException.NotFound.class.getSimpleName())
//                        .withErrorCode("Custom Error Code XXX").build(),
//                HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorDTO> handleMethodMismatchException(final InternalException exception) {
//        return new ResponseEntity<ErrorDTO>(
//                ErrorDTO.builder()
//                        .withTitle("Invalid Employee")
//                        .withDetail(exception.getMessage())
//                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                        .withErrorType(IllegalArgumentException.class.getSimpleName())
//                        .withErrorCode("Custom Error Code boolean").build(),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleOtherException(final Exception exception) {

        return new ResponseEntity<>(
                ErrorDTO.builder()
                        .withDetail(exception.getMessage())
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withErrorType(exception.getClass().getSimpleName())
                        .withErrorCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus httpStatus, WebRequest webRequest){
        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
