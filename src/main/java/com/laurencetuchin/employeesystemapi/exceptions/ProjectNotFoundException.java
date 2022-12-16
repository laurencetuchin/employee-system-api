package com.laurencetuchin.employeesystemapi.exceptions;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(String message) {
        super(message);
    }

}
