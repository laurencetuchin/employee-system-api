package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void findByNameIgnoreCaseContains() {
    }

    @Test
    void findByRoleIgnoreCaseContains() {
    }

    @Test
    void findCurrentlyEmployedEmployees() {
    }

    @Test
    void findEmployeeByNameAndRole() {
    }

    @Test
    void findEmployeeById() {
    }

    @Test
    void getAllEmployees() {
    }

    @Test
    void getAllEmployeesDTO() {
    }

    @Test
    void getEmployeeDTOById() {
    }

    @Test
    void save() {
    }

    @Test
    void getEmployeeById() {
    }

    @Test
    void addNewEmployee() {
    }

    @Test
    void deleteEmployeeById() {
    }

    @Test
    void updateEmployeeById() {
    }
}