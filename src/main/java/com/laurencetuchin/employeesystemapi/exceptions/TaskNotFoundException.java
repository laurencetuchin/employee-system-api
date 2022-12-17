package com.laurencetuchin.employeesystemapi.exceptions;

public class TaskNotFoundException extends RuntimeException {


    public TaskNotFoundException(String message) {
        super(message);
    }

}

