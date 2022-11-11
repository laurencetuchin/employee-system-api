package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDTO;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeServiceTest {

//    @InjectMocks
//    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

//    @Test
//    void itLoads(){
//        assertThat(employeeService).isNotNull();
//    }

//    @BeforeEach
//    void setup() {
//        EmployeeService employeeService1 = new EmployeeService(employeeRepository);
//    }

    @Test
    void findByNameIgnoreCaseContains() {
        Employee employee = new Employee("Cristiano Ronaldo", "Striker", true);
        List<Employee> targetEmployee = Collections.singletonList(employee);
//        List<Employee> findEmployeeWithName = employeeService.findByNameIgnoreCaseContains("Cristiano");
        List<Employee> findEmployeeRepoWithName = employeeRepository.findByNameIgnoreCaseContains("Cristiano");

//        assertThat(findEmployeeWithName).isEqualTo(targetEmployee);
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
//        Optional<Employee> returnedEmployee = employeeService.findEmployeeById(1L);
//        Employee returnedEmployee2 = employeeService.findEmployeeById(1L).get();
        // then

//        assertTrue(returnedEmployee.isPresent(), "Employee was not found");
//        assertSame(returnedEmployee.get(), employee, "The employee returned was not the same as the mock");
    }

    @Test
    void getAllEmployees() {
        Employee employee = new Employee("Cristiano Ronaldo", "Striker", true);
        employeeRepository.save(employee);
        EmployeeService service = new EmployeeService(employeeRepository);

        List<Employee> employeeList = service.getAllEmployees();
        Employee onlyEmployee = employeeList.get(employeeList.size() - 1);

        assertEquals(employee.getName(),onlyEmployee.getName());

    }

    @Test
    void getAllEmployeesDTO() {
        // given
        Employee employee = new Employee("Cristiano Ronaldo", "Striker", true);
        // when
        EmployeeService service = new EmployeeService(employeeRepository);

        List<EmployeeDTO> totalDto = employeeRepository.findAll().stream().map(EmployeeDTO::new).collect(Collectors.toList());
        List<EmployeeDTO> employeeDTOList = service.getAllEmployeesDTO();
        List<EmployeeDTO> employeeDTOList1 = service.getAllEmployeesDTO();
        // then
        assertThat(employeeDTOList).isEqualTo(employeeDTOList1);


    }

    @Test
    void getEmployeeDTOById() {
    }

    @Test
    void save() {
        Employee employee = new Employee("Cristiano Ronaldo", "Striker", true);
//        employeeService.save(employee);


        when(employeeRepository.save(employee)).thenReturn(employee);
//        List<Employee> allEmployees = employeeService.getAllEmployees();
        List<Employee> allEmployees1 = employeeRepository.findAll();
//        assertThat(allEmployees).isNotNull();
//        assertEquals(employee.getName(), employeeService.getAllEmployees().stream().filter(o -> Boolean.parseBoolean(employee.getName())).collect(Collectors.toList()));
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