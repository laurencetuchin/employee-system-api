package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest {


    @Autowired
    private EmployeeRepository underTest;

    @Test
    void itShouldFindByNameIgnoreCaseContains() {

        // given
        Employee employee = new Employee(
                "Tom",
                "Destroying Batman",
                true
        );
        underTest.save(employee);
        // when
        List<Employee> correctName = underTest.findByNameIgnoreCaseContains("Tom");

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