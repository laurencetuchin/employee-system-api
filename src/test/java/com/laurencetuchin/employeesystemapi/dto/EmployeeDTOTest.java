package com.laurencetuchin.employeesystemapi.dto;

import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDTOTest {

//    private final EmployeeDTO employeeDTO = new EmployeeDTO();

    @Autowired
    private EmployeeDTO employeeDTO;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void getEmployeeName() {

    }

    @Test
    void getEmployeeRole() {
        // given

        // when

        // then
    }
}