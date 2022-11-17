package com.laurencetuchin.employeesystemapi.entities;

import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private static Validator validator;

    @BeforeTestClass
    public static void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
}