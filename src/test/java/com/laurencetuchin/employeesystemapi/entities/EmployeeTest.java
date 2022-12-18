package com.laurencetuchin.employeesystemapi.entities;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootTest
class EmployeeTest {


    private static Validator validator;

    @BeforeTestClass
    public static void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

//    @Test
//    public void nameIsNull() {
//        Employee employee = new Employee(null, "Car Driver", true);
//
//        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);
//
//        assertEquals(1, constraintViolations.size());
//        assertEquals("may not be null", constraintViolations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void nameIsTooLong() {
//        Employee employee = new Employee("12345678901012", "Car Driver", true);
//
//        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);
//
//        assertEquals(0, constraintViolations);
//
//    }
}