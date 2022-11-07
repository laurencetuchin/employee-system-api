package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class EmployeeRepositoryTest {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void save() {
        Employee employee1 = new Employee();
        employee1.setName("Sarah Peterson");
        employee1.setRole("Executive Producer");
        employeeRepository.save(employee1);

        assertNotNull(employee1);
        assertThat(employee1.getName()).isEqualTo("Sarah Peterson");
    }

    @Test
    void update(){
        Employee employee1 = new Employee();
        employee1.setName("Sarah Peterson");
        employee1.setRole("Executive Producer");
        employeeRepository.save(employee1);
        // saves Employee
        assertThat(employee1.getName()).isEqualTo("Sarah Peterson");
        // checks Employee saved has correct information

        employee1.setName("Sarah Petoria");
        employeeRepository.save(employee1);
        // saves Employee with updated name
        assertThat(employee1.getName()).isEqualTo("Sarah Petoria");
        // checks that Employee is saved with updated name
    }

    @Test
    void itShouldUpdateRoleWhenSaved() {

        Employee employee1 = new Employee();
        employee1.setName("Sarah Peterson");
        employee1.setRole("Executive Producer");
        employeeRepository.save(employee1);
        // saves Employee
        assertThat(employee1.getRole()).isEqualTo("Executive Producer");
        // checks Employee saved has correct information

        employee1.setRole("Vice Executive Producer");
        employeeRepository.save(employee1);
        // saves Employee with updated name
        assertThat(employee1.getRole()).isEqualTo("Vice Executive Producer");
        // checks that Employee is saved with updated role
    }

    @Test
    void itShouldSaveThreeEmployeesToRepository() {
        Employee employee1 = new Employee();
        employee1.setName("Sarah Peterson");
        employee1.setRole("Executive Producer");
        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setName("Marcus Rashford");
        employee2.setRole("Left Winger");
        employeeRepository.save(employee2);

        Employee employee3 = new Employee();
        employee3.setName("Cristiano Ronaldo");
        employee3.setRole("Striker");
        employeeRepository.save(employee3);

        assertThat(employeeRepository.count()).isEqualTo(3);

    }

    @Test
    void itShouldGiveEmployeesAnIncrementalId() {
        Employee employee1 = new Employee();
        employee1.setName("Sarah Peterson");
        employee1.setRole("Executive Producer");
        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setName("Marcus Rashford");
        employee2.setRole("Left Winger");
        employeeRepository.save(employee2);

        Employee employee3 = new Employee();
        employee3.setName("Cristiano Ronaldo");
        employee3.setRole("Striker");
        employeeRepository.save(employee3);

        assertThat(employee1.getId()).isEqualTo(1);
        assertThat(employee2.getId()).isNotEqualTo(1);
        assertThat(employee2.getId()).isEqualTo(2);
        assertThat(employee3.getId()).isEqualTo(3);

    }

    @Test
    void itShouldGiveADefaultEmploymentStatusOfTrue() {
        Employee employee1 = new Employee();
        employee1.setName("Sarah Peterson");
        employee1.setRole("Executive Producer");
        employeeRepository.save(employee1);

        assertThat(employee1.isCurrentlyWorkingAtCompany()).isTrue();
    }

    @Test
    void itShouldAssignAFalseValueWhenSpecified() {
        Employee employee1 = new Employee();
        employee1.setName("Sarah Peterson");
        employee1.setRole("Executive Producer");
        employee1.setCurrentlyWorkingAtCompany(false);
        employeeRepository.save(employee1);

        assertThat(employee1.isCurrentlyWorkingAtCompany()).isFalse();
        assertThat(employee1.isCurrentlyWorkingAtCompany()).isEqualTo(false);
    }

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
        List<Employee> correctLowercaseName = employeeRepository.findByNameIgnoreCaseContains("tom");
        List<Employee> correctUppercaseName = employeeRepository.findByNameIgnoreCaseContains("TOM");
        List<Employee> correctMixedcaseName = employeeRepository.findByNameIgnoreCaseContains("ToM");

        // then
        assertThat(correctLowercaseName.listIterator().next().getName()).isEqualTo(employee.getName());
        assertThat(correctLowercaseName.listIterator().next().getName()).isEqualTo("Tom");
        assertThat(correctUppercaseName.listIterator().next().getName()).isEqualTo(employee.getName());
        assertThat(correctMixedcaseName.listIterator().next().getName()).isEqualTo(employee.getName());
    }

    @Test
    void itShouldNotReturnAnyMatchForFindByNameIgnoreCaseContains() {
        // given
        Employee employee3 = new Employee("Anne Hathaway","Catwoman");
        Employee employee1 = new Employee("Tom Hardy","Bane");
        Employee employee2 = new Employee("Christian Bale","Batman");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
        List<Employee> employees = new ArrayList<>();

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);


        List<Employee> correctLowercaseName = employeeRepository.findByNameIgnoreCaseContains("Amy");
        assertThat(correctLowercaseName).isEmpty();


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