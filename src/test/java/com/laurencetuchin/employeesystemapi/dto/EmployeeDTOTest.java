package com.laurencetuchin.employeesystemapi.dto;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeDTOTest {

//    private final EmployeeDTO employeeDTO = new EmployeeDTO();

    @MockBean
    private EmployeeDTO employeeDTO;

    @MockBean
    private Employee employee;

    @Autowired
    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    EmployeeDTOTest(Employee employee) {
        this.employee = employee;
    }

    @BeforeEach
    void setup() {
        List<Employee> employee1 = Collections.singletonList(new Employee("Cristiano Ronaldo", "Striker", true));
        employeeRepository.saveAllAndFlush(employee1);


    }

    @Test
    void checkEmployeeIsSaved() {

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).isEqualTo(1);
    }

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