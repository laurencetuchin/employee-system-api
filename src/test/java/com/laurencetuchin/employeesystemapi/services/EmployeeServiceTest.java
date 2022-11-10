package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

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
        // given
        Employee employee = new Employee("Cristiano Ronaldo", "Striker", true);
        doReturn(Optional.of(employee)).when(employeeRepository).findById(1L);
        // when
        Optional<Employee> returnedEmployee = employeeService.findEmployeeById(1L);
        // then

        assertTrue(returnedEmployee.isPresent(), "Employee was not found");
        assertSame(returnedEmployee.get(), employee, "The employee returned was not the same as the mock");
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