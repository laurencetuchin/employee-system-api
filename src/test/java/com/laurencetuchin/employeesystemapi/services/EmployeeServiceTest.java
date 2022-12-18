package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.EmploymentStatus;
import com.laurencetuchin.employeesystemapi.exceptions.EmployeeNotFoundException;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        if (employeeService.getAllEmployees().size() < 7) {
            Employee employee = new Employee("Cristiano Ronaldo", "Striker", "cristiano@gmail.com", EmploymentStatus.unemployed, LocalDate.of(1985,12,12),"Win everything");
            employeeService.save(employee);
        }
    }

    @Test
    void findByNameIgnoreCaseContains() {
        // given
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        // when
        Employee employee = employeeService.getAllEmployees().get(6);
        List<Employee> findEmployeeWithName = employeeService.findByName("Cristiano");

        // then
        // checks that expected name matches
        assertEquals(findEmployeeWithName.get(0).getName(),employee.getName());
        assertEquals("Cristiano Ronaldo",employee.getName());
        // checks that expected size matches
        assertEquals(1, findEmployeeWithName.size());
        assertEquals(1, employeeService.findByName("FrOdo").size());
    }

    @Test
    void findByRoleIgnoreCaseContains() {
        // given
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // when
        List<Employee> byRoleIgnoreCaseContains = employeeService.findByRole("STriker");
        String role = byRoleIgnoreCaseContains.get(0).getRole();
        // then
        // checks role matches expected case
        assertEquals("Striker", role);
        // checks size of Employee is expected
        assertEquals(1, byRoleIgnoreCaseContains.size());
        assertEquals(3, employeeService.findByRole("riNg").size());
    }

    @Test
    void findByRoleIgnoreCaseContainsWhenLowercase() {
        // given
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // when
        List<Employee> byRoleIgnoreCaseContains = employeeService.findByRole("striker");
        String role = byRoleIgnoreCaseContains.get(0).getRole();

        // then
        // checks role matches expected role
        assertEquals("Striker", role);
        // checks role matches expected size
        assertEquals(1, byRoleIgnoreCaseContains.size());
        assertEquals(3, employeeService.findByRole("ring").size());

    }

    @Test
    void findByRoleIgnoreCaseContainsWhenUppercase(){
        // given
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // when
        List<Employee> byRoleIgnoreCaseContains = employeeService.findByRole("STRIKER");
        String role = byRoleIgnoreCaseContains.get(0).getRole();
        // then
        // checks match expected role
        assertEquals("Striker", role);
        // checks match expected size
        assertEquals(1, byRoleIgnoreCaseContains.size());
        assertEquals(2, employeeService.findByRole("RING").size());
    }

    @Test
    void findByRoleIgnoreCaseContainsWhenUpperLowercase() {
        // given
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // when
        List<Employee> byRoleIgnoreCaseContains = employeeService.findByRole("StRiKeR");
        String role = byRoleIgnoreCaseContains.get(0).getRole();
        // then
        // checks expected match for role
        assertEquals("Striker", role);
        // checks expected match for employee size
        assertEquals(1, byRoleIgnoreCaseContains.size());
        assertEquals(3,employeeService.findByRole("RiNg").size());

    }

    @Test
    void findByEmploymentStatusEmployed() {
        // given
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        String status = "employed";
        // when
        List<Employee> currentlyEmployedEmployees = employeeService.findByStatus(EmploymentStatus.employed);
        List<Employee> totalEmployees = employeeService.getAllEmployees();
        employeeService.getAllEmployees();
        int size = currentlyEmployedEmployees.size();
        assertThat(4).isEqualTo(size);
//        assertEquals(totalEmployees.size(), size);
    }

    @Test
    void findCurrentlyEmployedEmployeesThatAreUnemployed(){
        // given
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee = new Employee(
                "Alphonso Davies",
                "Left Winger",
                "davies@gmail.com",
                EmploymentStatus.unemployed,
                LocalDate.of(2001,1,1),
                "Win win win"
        );
        employeeService.save(employee);
        // when
        List<Employee> currentlyUnemployed = employeeService.findByStatus(EmploymentStatus.unemployed);
        Employee employee1 = currentlyUnemployed.get(0);
        employeeService.getAllEmployees();

        assertThat(currentlyUnemployed).isNotNull();
        assertEquals(EmploymentStatus.unemployed,employee1.getStatus());

        Employee employee2 = new Employee(
                "Bukayo Saka",
                "Right winger",
                "bukayo@gmail.com",
                EmploymentStatus.unemployed,
                LocalDate.of(2011,1,1),
                "Leave Arsenal"
        );
        employeeService.save(employee2);

        List<Employee> allFalseWorkingEmployees = employeeService.findByStatus(EmploymentStatus.unemployed);
        assertEquals(4, allFalseWorkingEmployees.size());
    }

    @Test
    void findEmployeeByNameAndRole() {
        // given
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        // when
        List<Employee> employeeListByNameAndRole = employeeService.findEmployeeByNameAndRole("Cristiano Ronaldo","Striker");


        // then
        assertEquals(1, employeeListByNameAndRole.size());

    }


    @Test
    void getAllEmployees() {
        // given
        EmployeeService service = new EmployeeService(employeeRepository);
        // when
        List<Employee> employeeList = service.getAllEmployees();
        // then
        // checks employee list has employees
        assertThat(employeeList).isNotNull();
        // checks employee size is correct
        assertEquals(9, employeeList.size());

    }


    @Test
    void save() {
        // given
        Employee employee = new Employee(
                "Frenkie De Jong",
                "Box to Box Midfielder",
                "frenkie@gmail.com",
                EmploymentStatus.employed,
                LocalDate.of(2000,5,5),
                "Stay at Barca"
        );
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // when
        Employee employee1 = employeeService.save(employee);


        // then
        assertThat(employee1.getName()).isEqualTo(employee.getName());
        assertThat(employee1.getRole()).isEqualTo(employee.getRole());
        assertThat(employee1.getStatus()).isEqualTo(employee.getStatus());
        // checks ID automatically generated
        assertThat(employee1.getId()).isNotNull();

    }


    @Test
    void addNewEmployee() {
        // given
        Employee employee = new Employee(
                "Lionel Messi",
                "Playmaker",
                "messigoat@gmail.com",
                EmploymentStatus.employed,
                LocalDate.of(1987,2,2),
                "Win world cup"
        );
        EmployeeService service = new EmployeeService(employeeRepository);
        // when
        service.createEmployee(employee);
        // get last employee
        int size = service.getAllEmployees().size();
        // then
        assertThat(service.getAllEmployees().get(size - 1).getName()).isEqualTo("Lionel Messi");
    }

    @Test
    void updateEmployeeById() {
        // given
        EmployeeService service = new EmployeeService(employeeRepository);
        Optional<Employee> employeeById = service.findEmployeeById(1L);
        Employee employee = service.getAllEmployees().get(0);
        boolean employeeExists = employeeRepository.existsById(employee.getId());
        if (!employeeExists) {
            throw new NoSuchElementException("Employee with id" + employee.getId() + "does not exist");
        } else {

        String employeeName = employee.getName();
        // total list only 10 after deleting
        List<Employee> allEmployees = service.getAllEmployees();
//        String previousName = employeeById.get().getName();


        // when
        assertThat(employee.getId()).isEqualTo(2L);
        assertThat(employeeName).isEqualTo("Bilbo");
        employee.setName("Tomato Man");
        employee.setRole("Burger Shop King");
        service.updateEmployeeById(employee,2L);
        // then
        assertThat(employee.getName()).isEqualTo("Tomato Man");
        assertThat(employee.getRole()).isEqualTo("Burger Shop King");
        }



    }

    @Test
    void updateEmployeeByIdThrowsNoSuchElementException() {
        // given
        EmployeeService service = new EmployeeService(employeeRepository);

        // when
        Optional<Employee> employeeById = service.findEmployeeById(1L);
//        Employee employee = employeeById.get();

        // then
        assertThrows(NoSuchElementException.class, () -> {
            service.updateEmployeeById(employeeById.get(),100L);
        });

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
        assertThat(employeeByIdDeleted.isEmpty()).isTrue();

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


    @BeforeTestClass
    public static void setupValidatorInstance(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void findEmployeeByNameOrRole() {
        // given
        EmployeeService service = new EmployeeService(employeeRepository);
        Employee employee = service.getAllEmployees().get(6);

        // when
        List<Employee> nameFound = service.findEmployeeByNameOrRole("Cristiano Ronaldo",null);
        List<Employee> roleFound = service.findEmployeeByNameOrRole(null,"Striker");
        // then

        assertThat(employee.getName()).isEqualTo(nameFound.get(0).getName());
        assertThat(employee.getRole()).isEqualTo(roleFound.get(0).getRole());
    }



}