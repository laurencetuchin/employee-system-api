package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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