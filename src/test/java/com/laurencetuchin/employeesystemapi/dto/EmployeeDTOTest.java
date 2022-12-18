package com.laurencetuchin.employeesystemapi.dto;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.EmploymentStatus;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeDTOTest {

//    private final EmployeeDTO employeeDTO = new EmployeeDTO();

    @MockBean
    private EmployeeDto employeeDto;

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
        List<Employee> employee1 = Collections.singletonList(new Employee("Cristiano Ronaldo", "Striker", "cristiano@gmail.com", EmploymentStatus.unemployed, LocalDate.of(1985,1, 11),"win everything"));
        employeeRepository.saveAllAndFlush(employee1);
        //                .collect(Collectors.toList());

    }

    @Test
    void checkEmployeeIsSaved() {
        List<EmployeeDto> employeeDTOList = employeeService.getAllEmployeesDTO();
        List<Employee> employeeList = employeeRepository.findAll();
//        List<EmployeeDTO> employeeDTOList = employeeRepository.
        assertThat(employeeList.stream().count()).isEqualTo(1);
    }

    @Test
    void itShouldGetEmployeeNameAsEmployeeDTO() {
    // List<EmployeeDTO> getAllEmployeesAsDTO = employeeService.getAllEmployees()
        //                        .stream()
        //                        .map(EmployeeDTO::new)
        //                        .collect(Collectors.toList());
        //        assertThat(getAllEmployeesAsDTO).isNotNull();
        //        assertThat(getAllEmployeesAsDTO).isInstanceOf(List.class);

    }

    @Test
    void getEmployeeRole() {
        // given

        // when

        // then
        List<Employee> totalEmployees = employeeRepository.findAll();
        List<Employee> expected = employeeRepository.findAll().stream().filter(employee1 -> employee1.getName().matches("Cristiano Ronaldo")).collect(Collectors.toList());
    //    assertThat(employeeDTO.getEmployeeName()).isEqualTo(expected);
    }
}