package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDTO;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.mappers.EmployeeMapper;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeServiceTest {
    private static Validator validator;

//    @InjectMocks
//    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    void itLoads(){
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        assertThat(employeeService).isNotNull();
    }

    @BeforeEach
    void setup() {
        EmployeeService employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void findByNameIgnoreCaseContains() {
        // given
        Employee employee = new Employee("Cristiano Ronaldo", "Striker", true);
        List<Employee> targetSingletonEmployee = Collections.singletonList(employee);
        List<Employee> newEmployeeList = new ArrayList<>();
        newEmployeeList.add(employee);

        employeeRepository.save(employee);
        // when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> targetEmployee = employeeRepository.findByNameIgnoreCaseContains("Cristiano");

        List<Employee> findEmployeeWithName = employeeService.findByNameIgnoreCaseContains("Cristiano");
        List<Employee> findEmployeeRepoWithName = employeeRepository.findByNameIgnoreCaseContains("Cristiano");


//        assertThat(findEmployeeWithName.get(0)).(newEmployeeList.get(0));
        assertEquals(findEmployeeWithName.get(0).getName(),employee.getName());
        assertEquals("Cristiano Ronaldo",employee.getName());
//        assertThat(findEmployeeWithName).isEqualTo(targetEmployee);
    }

    @Test
    void findByRoleIgnoreCaseContains() {
        Employee employee = new Employee("Cristiano Ronaldo", "Number 12", true);
        employeeRepository.save(employee);


        EmployeeService employeeService = new EmployeeService(employeeRepository);

        assertEquals("Number 12", employee.getRole());
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
        EmployeeDTO mapper = new EmployeeMapper().toDto(employee);

//        List<EmployeeDTO> totalDto = employeeRepository.findAll().stream().map(EmployeeDTO::new).collect(Collectors.toList());
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
    void addNewEmployee() {
    }

    @Test
    void deleteEmployeeById() {
        // given
        EmployeeService service = new EmployeeService(employeeRepository);
        // when
        Optional<Employee> employeeById = service.findEmployeeById(1L);
        assertThat(employeeById).isNotEmpty();

        service.deleteEmployeeById(1L);
        List<Employee> employeeByIdDeleted = service.findEmployeeById(1L).stream().collect(Collectors.toList());
//        employeeRepository.deleteById(100L);
        // then
        assertThat(employeeByIdDeleted).isEmpty();

    }

    @Test
    void deleteEmployeeByIdThrowsIllegalStateExceptionIfNotExists() {
        // given
        EmployeeService service = new EmployeeService(employeeRepository);

        // when
        Optional<Employee> employeeByIdNotExists = service.findEmployeeById(100L);
        // then
        Long id = 100L;
        String message = "Employee with id " + id + "does not exist";
        assertThrows(IllegalStateException.class,() -> {
            service.deleteEmployeeById(id);
        }, message);

    }

    @Test
    void updateEmployeeById() {
        // given
        EmployeeService service = new EmployeeService(employeeRepository);
        Optional<Employee> employeeById = service.findEmployeeById(1L);
        String previousName = employeeById.get().getName();
        // when
        assertThat(previousName).isEqualTo("Frodo");
        employeeById.get().setName("Tomato Man");
        employeeById.get().setRole("Burger Shop King");

        assertThat(employeeById.get().getName()).isEqualTo("Tomato Man");
        assertThat(employeeById.get().getRole()).isEqualTo("Burger Shop King");
    }

    @Test
    void updateEmployeeByIdThrowsIllegalStateException() {
        // given
        EmployeeService service = new EmployeeService(employeeRepository);

        // when
        Optional<Employee> employeeById = service.findEmployeeById(100L);

        // then
        assertThrows(NoSuchElementException.class, () -> {
            service.updateEmployeeById(employeeById.get());
        });

    }

    @Test
    void updateEmployeeByIdNotNull(){

    }

    @BeforeTestClass
    public static void setupValidatorInstance(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}