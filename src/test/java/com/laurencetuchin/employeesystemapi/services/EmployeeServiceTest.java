package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDTO;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void itLoads(){
        assertThat(employeeService).isNotNull();
    }

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
        Employee returnedEmployee2 = employeeService.findEmployeeById(1L).get();
        // then

        assertTrue(returnedEmployee.isPresent(), "Employee was not found");
        assertSame(returnedEmployee.get(), employee, "The employee returned was not the same as the mock");
    }

    @Test
    void getAllEmployees() {
    }

    @Test
    void getAllEmployeesDTO() {
        // given
        Employee employee = new Employee("Cristiano Ronaldo", "Striker", true);
        // when
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployeesDTO();
        List<EmployeeDTO> employeeDTOList1 = employeeService.getAllEmployees().stream().map(EmployeeDTO::new).collect(Collectors.toList());
        // then
        assertThat(employeeDTOList).isEqualTo(employeeDTOList1);


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