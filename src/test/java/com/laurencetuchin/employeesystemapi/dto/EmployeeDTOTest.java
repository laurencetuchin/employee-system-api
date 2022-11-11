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
import java.util.stream.Collectors;

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

        List<Employee> employee4 = employeeRepository.findAll();
        List<Employee> employee3 = employeeService.getAllEmployees();
        List<Employee> employeeDTO = employeeService.getAllEmployees().stream().collect(Collectors.toList());
        EmployeeDTO employeeDTO1 = new EmployeeDTO(new Employee("Cristiano Ronaldo","Striker",true));
        List<Employee> employee2 = employeeRepository.findAll().stream().collect(Collectors.toList());


        List<EmployeeDTO> employeeDTOList1 = employeeService.getAllEmployees()
                .stream()
                .map(EmployeeDTO::new)
                .collect(Collectors.toList());

    }

    @Test
    void checkEmployeeIsSaved() {

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList.stream().count()).isEqualTo(1);
    }

    @Test
    void itShouldGetEmployeeNameAsEmployeeDTO() {

        List<EmployeeDTO> getAllEmployeesAsDTO = employeeService.getAllEmployees()
                        .stream()
                        .map(EmployeeDTO::new)
                        .collect(Collectors.toList());
        assertThat(getAllEmployeesAsDTO).isNotNull();
        assertThat(getAllEmployeesAsDTO).isInstanceOf(List.class);
    }

    @Test
    void getEmployeeRole() {
        // given

        // when

        // then
        List<Employee> totalEmployees = employeeRepository.findAll();
        List<Employee> expected = employeeRepository.findAll().stream().filter(employee1 -> employee1.getName().matches("Cristiano Ronaldo")).collect(Collectors.toList());
        assertThat(employeeDTO.getEmployeeName()).isEqualTo(expected);
    }
}