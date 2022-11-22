package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
            Employee employee = new Employee("Cristiano Ronaldo", "Striker", true);
            employeeService.save(employee);
        }
    }

    @Test
    void findByNameIgnoreCaseContains() {
        // given
        Employee employee = new Employee("Cristiano Ronaldo", "Striker", true);
//        List<Employee> targetSingletonEmployee = Collections.singletonList(employee);
//        List<Employee> newEmployeeList = new ArrayList<>();
//        newEmployeeList.add(employee);

//        employeeRepository.save(employee);
        // when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
//        List<Employee> targetEmployee = employeeRepository.findByNameIgnoreCaseContains("Cristiano");

        List<Employee> findEmployeeWithName = employeeService.findByNameIgnoreCaseContains("Cristiano");
//        List<Employee> findEmployeeRepoWithName = employeeRepository.findByNameIgnoreCaseContains("Cristiano");


//        assertThat(findEmployeeWithName.get(0)).(newEmployeeList.get(0));
        assertEquals(findEmployeeWithName.get(0).getName(),employee.getName());
        assertEquals("Cristiano Ronaldo",employee.getName());
//        assertThat(findEmployeeWithName).isEqualTo(targetEmployee);
    }

    @Test
    void findByRoleIgnoreCaseContains() {
        // given
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // when
        List<Employee> byRoleIgnoreCaseContains = employeeService.findByRoleIgnoreCaseContains("Striker");
        String role = byRoleIgnoreCaseContains.get(0).getRole();
        // then
        // checks role matches expected case
        assertEquals("Striker", role);
        // checks size of Employee is expected
        assertEquals(1, byRoleIgnoreCaseContains.size());
        assertEquals(3, employeeService.findByRoleIgnoreCaseContains("ring").size());
    }

    @Test
    void findByRoleIgnoreCaseContainsWhenLowercase() {
        // given
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // when
        List<Employee> byRoleIgnoreCaseContains = employeeService.findByRoleIgnoreCaseContains("striker");
        String role = byRoleIgnoreCaseContains.get(0).getRole();

        // then
        // checks role matches expected role
        assertEquals("Striker", role);
        // checks role matches expected size
        assertEquals(1, byRoleIgnoreCaseContains.size());

    }

    @Test
    void findByRoleIgnoreCaseContainsWhenUppercase(){
        // given
        Employee employee = new Employee("Cristiano Ronaldo", "Number 12", true);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.save(employee);
        // when
        List<Employee> byRoleIgnoreCaseContains = employeeService.findByRoleIgnoreCaseContains("NUMBER 12");
        String role = byRoleIgnoreCaseContains.get(0).getRole();
        // then
        assertEquals("Number 12", role);
    }

    @Test
    void findByRoleIgnoreCaseContainsWhenUpperLowercase() {
        // given
        Employee employee = new Employee("Cristiano Ronaldo", "Best Striker!",true);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.save(employee);
        // when
        List<Employee> byRoleIgnoreCaseContains = employeeService.findByRoleIgnoreCaseContains("BeSt StRiKeR!");
        String role = byRoleIgnoreCaseContains.get(0).getRole();
        // then
        assertEquals("Best Striker!", role);
    }

    @Test
    void findCurrentlyEmployedEmployeesThatAreTrue() {
        // given
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        // when
        List<Employee> currentlyEmployedEmployees = employeeService.findCurrentlyEmployedEmployees(true);
        List<Employee> totalEmployees = employeeService.getAllEmployees();

        assertThat(7).isEqualTo(currentlyEmployedEmployees.size());
        assertEquals(totalEmployees.size(), currentlyEmployedEmployees.size());
    }

    @Test
    void findCurrentlyEmployedEmployeesThatAreFalse(){
        // given
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee = new Employee("Alphonso Davies", "Left Winger", false);
        employeeService.save(employee);
        // when
        List<Employee> currentlyNonEmployedEmployees = employeeService.findCurrentlyEmployedEmployees(false);
        boolean currentlyWorkingAtCompany = currentlyNonEmployedEmployees.get(0).isCurrentlyWorkingAtCompany();

        assertThat(currentlyNonEmployedEmployees).isNotNull();
        assertEquals(false,currentlyWorkingAtCompany);

        Employee employee1 = new Employee("Bukayo Saka","Right winger", false);
        employeeService.save(employee1);

        List<Employee> allFalseWorkingEmployees = employeeService.findCurrentlyEmployedEmployees(false);
        assertEquals(2, allFalseWorkingEmployees.size());
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
        Employee employee = new Employee("Cristiano Ronaldo", "Striker", true);
        employeeRepository.save(employee);
        EmployeeService service = new EmployeeService(employeeRepository);

        List<Employee> employeeList = service.getAllEmployees();
        Employee onlyEmployee = employeeList.get(employeeList.size() - 1);

        assertEquals(employee.getName(),onlyEmployee.getName());

    }


    @Test
    void getEmployeeDTOById() {
    }

    @Test
    void save() {
        // given
        Employee employee = new Employee("Cristiano Ronaldo 2", "Striker", true);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // when
        Employee employee1 = employeeService.save(employee);


        // then
        assertThat(employee1.getName()).isEqualTo(employee.getName());
    }


    @Test
    void addNewEmployee() {
        // given
        Employee employee = new Employee("Lionel Messi","Playmaker",true);
        EmployeeService service = new EmployeeService(employeeRepository);
        // when
        service.addNewEmployee(employee);
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
        String employeeName = employee.getName();

//        String previousName = employeeById.get().getName();
        // when
        assertThat(employeeName).isEqualTo("Frodo");
        employee.setName("Tomato Man");
        employee.setRole("Burger Shop King");
//        employeeName.get().setName("Tomato Man");
//        employeeName.get().setRole("Burger Shop King");
        // then
        assertThat(employee.getName()).isEqualTo("Tomato Man");
        assertThat(employee.getRole()).isEqualTo("Burger Shop King");
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
    void updateEmployeeByIdNotNull(){

    }

    @BeforeTestClass
    public static void setupValidatorInstance(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void findEmployeeByNameOrRole() {
        // given
        Employee employee = new Employee("Cristiano Ronaldo", "Striker", true);
        employeeRepository.save(employee);
        EmployeeService service = new EmployeeService(employeeRepository);

        // when
        List<Employee> nameFound = service.findEmployeeByNameOrRole("Cristiano Ronaldo",null);
        List<Employee> roleFound = service.findEmployeeByNameOrRole(null,"Striker");
        // then

        assertThat(employee.getName()).isEqualTo(nameFound.get(0).getName());
        assertThat(employee.getRole()).isEqualTo(roleFound.get(0).getRole());
    }
}