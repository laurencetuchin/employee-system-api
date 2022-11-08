package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {


    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup(){
        Employee employee1 = new Employee("Cristiano Ronaldo","Striker",true);
        Employee employee2 = new Employee("Marcus Rashford", "Left winger",true);
        Employee employee3 = new Employee("Zlatan Ibrahimovic","Striker",false);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

//        employeeRepository.saveAllAndFlush(employees);
        employeeRepository.saveAll(employees);

    }

    @Test
    void itShouldCreateThreeEmployeesForSetup(){
        Long totalEmployees = employeeRepository.count();
        assertThat(totalEmployees).isEqualTo(3);
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

        long employeesTotal = employeeRepository.count();
        assertThat(employeesTotal).isEqualTo(3);

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
    void itShouldReturnThreeMatchesForFindByNameIgnoreCaseContains() {
        // given
        Employee employee3 = new Employee("anne Hathaway","Catwoman");
        Employee employee1 = new Employee("Anne hathaway","Bane");
        Employee employee2 = new Employee("anNe holloway","Batman");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        List<Employee> employees = new ArrayList<>();

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        List<Employee> correctLowercaseName = employeeRepository.findByNameIgnoreCaseContains("anne");
        assertThat(correctLowercaseName.stream().count()).isEqualTo(3);
    }

    @Test
    void itShouldReturnTwoMatchesForLastNameForFindByNameIgnoreCaseContains(){
        // given
        Employee employee3 = new Employee("anne Hathaway","Catwoman");
        Employee employee1 = new Employee("Anne hathaway","Bane");
        Employee employee2 = new Employee("anNe holloway","Batman");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        List<Employee> employees = new ArrayList<>();

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        List<Employee> twoMatchesLastName = employeeRepository.findByNameIgnoreCaseContains("hathaway");
        assertThat(twoMatchesLastName.stream().count()).isEqualTo(2);
    }

    @Test
    void itShouldReturnListOfEmployeesForFindByNameIgnoreCaseContains(){
        // given
        Employee employee3 = new Employee("anne Hathaway","Catwoman");
        Employee employee1 = new Employee("Anne hathaway","Bane");
        Employee employee2 = new Employee("anNe holloway","Batman");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        List<Employee> employees = new ArrayList<>();

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        List<Employee> employeeList = employeeRepository.findAll();

        List<Employee> threeMatchesFirstName = employeeRepository.findByNameIgnoreCaseContains("anne");

        assertTrue(threeMatchesFirstName.containsAll(employeeList));
        assertThat(threeMatchesFirstName).isEqualTo(employeeList);
        assertThat(threeMatchesFirstName).isEqualTo(employees);
    }

    @Test
    void itShouldNotReturnAnyEmployeesForFindByNameIgnoreCaseContains() {
        // given
        Employee employee3 = new Employee("anne Hathaway","Catwoman");
        Employee employee1 = new Employee("Anne hathaway","Bane");
        Employee employee2 = new Employee("anNe holloway","Batman");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        List<Employee> employees = new ArrayList<>();

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        List<Employee> employeeList = employeeRepository.findAll();

        List<Employee> noMatches = employeeRepository.findByNameIgnoreCaseContains("Jar Jar Binks");
        assertThat(noMatches).isNotEqualTo(employeeList);
    }

    @Test
    void itShouldReturnAListForFindByNameIgnoreCaseContains(){
        // given
        Employee employee1 = new Employee("Anne hathaway","Bane");
        employeeRepository.save(employee1);


        // when
        List<Employee> optionalEmployee = employeeRepository.findByNameIgnoreCaseContains("Jar Jar Binks");
        // then
        assertThat(optionalEmployee).isInstanceOf(List.class);
        assertThat(optionalEmployee).isNotInstanceOf(String.class);
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