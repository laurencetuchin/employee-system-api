package com.laurencetuchin.employeesystemapi.exceptions;

public class EmployeeNotFoundException extends RuntimeException {


    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
