package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.EmploymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class EmployeeRepositoryTest {


    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        Employee employee1 = new Employee("Cristiano Ronaldo", "Striker", "cristino@gmail.com", EmploymentStatus.unemployed, LocalDate.of(1985, 12, 12), "Win everything");
        Employee employee2 = new Employee("Marcus Rashford", "Left winger", "rashy@gmail.com", EmploymentStatus.onleave, LocalDate.of(2001, 1, 1), "Win at Manchester");
        Employee employee3 = new Employee("Zlatan Ibrahimovic", "Striker", "zlatan@gmail.com", EmploymentStatus.employed, LocalDate.of(1951, 11, 11), "Be juman");

        // Employee 4,5,6 used for edge case Search matching
        Employee employee4 = new Employee("anne Hathaway", "Catwoman");
        Employee employee5 = new Employee("ANNE hathaway", "Bane");
        Employee employee6 = new Employee("anNe holloway", "Batman");

        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);
        employees.add(employee6);

//        employeeRepository.saveAllAndFlush(employees);
        employeeRepository.saveAll(employees);

    }

    @Test
    void itShouldCreateThreeEmployeesForSetup() {
        Long totalEmployees = employeeRepository.count();
        assertThat(totalEmployees).isEqualTo(6);
    }


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
    void update() {
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

        long employeesTotal = employeeRepository.count();
        assertThat(employeesTotal).isEqualTo(6);

    }

    @Test
    void itShouldGiveEmployeesAnIncrementalId() {

        Employee employee1 = employeeRepository.getReferenceById(1L);
        Employee employee2 = employeeRepository.getReferenceById(2L);
        Employee employee3 = employeeRepository.getReferenceById(3L);


        assertThat(employee1.getId()).isEqualTo(1);
        assertThat(employee2.getId()).isNotEqualTo(1);
        assertThat(employee2.getId()).isEqualTo(2);
        assertThat(employee3.getId()).isEqualTo(3);

    }


    @Test
    void itShouldFindByNameIgnoreCaseContains() {

        Employee employee1;
        // given
        Employee employee = new Employee(
                "Tom",
                "Destroying Batman",
                "tom@gmail.com",
                EmploymentStatus.employed,
                LocalDate.of(2001, 1, 1),
                "Save gotham"
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


        // when
        List<Employee> correctLowercaseName = employeeRepository.findByNameIgnoreCaseContains("Amy");
        // then
        assertThat(correctLowercaseName).isEmpty();


    }

    @Test
    void itShouldReturnThreeMatchesForFindByNameIgnoreCaseContains() {
        // given

        List<Employee> correctLowercaseName = employeeRepository.findByNameIgnoreCaseContains("anne");

        assertThat(correctLowercaseName.stream().count()).isEqualTo(3);
    }

    @Test
    void itShouldReturnTwoMatchesForLastNameForFindByNameIgnoreCaseContains() {
        List<Employee> twoMatchesLastName = employeeRepository.findByNameIgnoreCaseContains("hathaway");
        assertThat(twoMatchesLastName.stream().count()).isEqualTo(2);
    }

    @Test
    void itShouldReturnListOfEmployeesForFindByNameIgnoreCaseContains() {
        // given
        List<Employee> employeeList = employeeRepository.findAll();

        List<Employee> threeMatchesFirstName = employeeRepository.findByNameIgnoreCaseContains("anne");

//        assertTrue(threeMatchesFirstName.containsAll(employeeList));
//        assertThat(threeMatchesFirstName).isEqualTo(3);
//        assertThat(threeMatchesFirstName).isEqualTo(employees);
//        assertThat(threeMatchesFirstName).isSameAs(employeeList.subList(3,6));
//        assertEquals(threeMatchesFirstName);
//        assertThat(threeMatchesFirstName).isSameAs(employeeList.listIterator(4,5,6))
    }

    @Test
    void itShouldNotReturnAnyEmployeesForNoMatchFindByNameIgnoreCaseContains() {
        // given
        List<Employee> employeeList = employeeRepository.findAll();
        // when
        List<Employee> noMatches = employeeRepository.findByNameIgnoreCaseContains("Jar Jar Binks");
        // then
        assertThat(noMatches).isNotEqualTo(employeeList);
    }

    @Test
    void itShouldReturnAListForFindByNameIgnoreCaseContains() {

        // when
        List<Employee> optionalEmployee = employeeRepository.findByNameIgnoreCaseContains("anne");
        // then
        assertThat(optionalEmployee).isInstanceOf(List.class);
        assertThat(optionalEmployee).isNotInstanceOf(String.class);
    }


    @Test
    void itShouldFindByRoleIgnoreCaseContains() {
        // given
        List<Employee> employeeRole = employeeRepository.findByRoleIgnoreCaseContains("Left Winger"); // Actual role is "Left winger"
        // when

        List<Employee> expected = Collections.singletonList(employeeRepository.findAll().get(1));
        // then
        assertThat(employeeRole).isEqualTo(expected);


    }


    @Test
    void itShouldFindEmployeeByNameAndRole() {
        // given
        List<Employee> employeeNameAndRole = employeeRepository.findEmployeeByNameAndRole("Cristiano Ronaldo", "Striker");

        // when
        List<Employee> expectedEmployee = employeeRepository.findAll().stream().filter(employee -> employee.getRole().matches("Striker") && employee.getName().matches("Cristiano Ronaldo")).collect(Collectors.toList());

        // then
        assertThat(employeeNameAndRole).isEqualTo(expectedEmployee);
    }

    @Test
    void itShouldDeleteEmployeeById() {
        employeeRepository.deleteById(1L);
    }


}