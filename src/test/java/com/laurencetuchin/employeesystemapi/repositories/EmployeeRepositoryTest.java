package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeRepositoryTest {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void itShouldFindByNameIgnoreCaseContains() {

        // given
        Employee employee = new Employee(
                "Tom",
                "Destroying Batman",
                true
        );
        employeeRepository.save(employee);
        // when
        List<Employee> correctName = employeeRepository.findByNameIgnoreCaseContains("Tom");

        // then
        assertThat(correctName).isEqualTo(employee.getName());
    }

    @Test
    void itShouldFindByRoleIgnoreCaseContains() {
    }

    @Test
    void itShouldFindByIsCurrentlyWorkingAtCompany() {
    }

    @Test
    void itShouldFindEmployeeByNameAndRole() {
    }


}