package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDto;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.EmploymentStatus;
import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.exceptions.EmployeeNotFoundException;
import com.laurencetuchin.employeesystemapi.mappers.EmployeeMapper;
import com.laurencetuchin.employeesystemapi.repositories.TaskRepository;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee") // Need to add in API versioning
//@CrossOrigin(origins = "http://localhost:8081")
@Validated
@CrossOrigin(origins = "http://localhost:8081/*")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    @Autowired
    private TaskRepository taskRepository;

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    //    @GetMapping("/employees/all")
//    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String name) {
//        try {
//            List<Employee> employees = new ArrayList<Employee>();
//
//            if (!employeeService.getAllEmployees().isEmpty()) {
//                employeeService.getAllEmployees();
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch(Exception ex){
//            System.out.println(ex);
//        }
//        return new ResponseEntity<>
//    }
    @Operation(summary = "Get Employee by Id", description = "Get an Employee by Id", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content)})
    @GetMapping("/employee/{id}")
    public ResponseEntity getEmployeeById(@PathVariable("id") Long id) {
        Optional<Employee> employeeIfExists = employeeService.findEmployeeById(id);
        if (employeeIfExists.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id: " + id + " not found");
        }
        try {
            if (employeeIfExists.isPresent()) {

                return ResponseEntity.status(HttpStatus.OK).body(employeeIfExists.get());
//                return new ResponseEntity<>(employeeIfExists.get(), HttpStatus.OK);
            }
        } catch (EmployeeNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Employee Found");
//            throw new EmployeeNotFoundException("Employee not found");
        }
        return new ResponseEntity<>(employeeIfExists.get(), HttpStatus.FOUND);
    }



    @Operation(summary = "Get Employees", description = "Get all Employees", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employees not found",
                    content = @Content)})
    @GetMapping("/employees/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("Employees not found");
        }
        try {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(employees, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get Employees DTO", description = "Get all Employees DTO", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employees not found",
                    content = @Content)})
    @GetMapping("/employees/dto")
    public List<EmployeeDto> getAllEmployeesDTO() {
        List<EmployeeDto> employees = employeeService.getAllEmployees()
                .stream().map(employee -> employeeMapper.toDto(employee))
                .collect(Collectors.toList());
        return employees;
    }

    @Operation(summary = "Get Employees DTO", description = "Get all Employees DTO", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employees not found",
                    content = @Content)})
    @GetMapping("/employees/dto/{id}")
    public List<EmployeeDto> getAllEmployeesIDDTO(@PathVariable Long id) {
        List<EmployeeDto> employees = employeeService.findEmployeeById(id)
                .stream().map(employee -> employeeMapper.toDto(employee))
                .collect(Collectors.toList());
        return employees;
    }

    /// ** Refactor to ENUM
    @Operation(summary = "Get Employees by Employment Status", description = "Get Employees by Employment Status, requires a boolean of true or false", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDto.class))}),
            @ApiResponse(responseCode = "404", description = "Employees not found",
                    content = @Content)})
    @GetMapping("/employment-status")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByEmploymentStatus(@RequestParam EmploymentStatus status) {
        List<Employee> employeeStatus = employeeService.findByStatus(status);
        List<EmployeeDto> employeeDTOS = employeeStatus.stream().map(employee -> employeeMapper.toDto(employee)).collect(Collectors.toList());
        if (employeeStatus.isEmpty()) {
            throw new EmployeeNotFoundException("Employees not found");
        }
        try {
            return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(employeeDTOS, HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Get Employees by Name", description = "Get Employees by Name, case insensitive e.g. frodo, FRODO, FrOdO", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employees not found",
                    content = @Content)})
    @GetMapping("/search/name")
//    @ResponseBody
    ResponseEntity<List<Employee>> findByNameIgnoreCaseContains(@RequestParam String partialName) {
        List<Employee> employees = employeeService.findByName(partialName);
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with name: " + partialName + " not found");
        }
        try {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(employees, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get Employees by Role", description = "Get Employees by Role, case insensitive e.g. ring bearer, RING BEARER, RiNg BEaReR", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employees not found",
                    content = @Content)})
    @GetMapping("/search/role")
    ResponseEntity<List<Employee>> findByRoleIgnoreCaseContains(@RequestParam String role) {
        List<Employee> employees = employeeService.findByRole(role);
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employee with role: " + role + " found. Please try again with a different role");
        }
        try {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(employees, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get Employees by Name and Role", description = "Get Employees by Name and Role, ignores case contains name and role e.g. name: Frodo, role: Ring bearer", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employees not found",
                    content = @Content)})
    @GetMapping("/search/NameAndRole")
    ResponseEntity<List<Employee>> findEmployeeByNameAndRole(@RequestParam String name, @RequestParam String role) {

        List<Employee> employees = employeeService.findEmployeeByNameAndRole(name, role);
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with name: " + name + " or role: " + role + " not found. Please try again with new parameters.");
        }
        try {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Get Employees by Name and Role", description = "Get Employees by Name or Role, ignores case contains e.g. name: Frodo, role: Ring bearer", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employees not found",
                    content = @Content)})
    @GetMapping("/search/NameOrRole")
    ResponseEntity<List<Employee>> findEmployeeByNameOrRole(@RequestParam String name, @RequestParam String role) {

        List<Employee> employees = employeeService.findEmployeeByNameOrRole(name, role);
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with name: " + name + " or role: " + role + " not found. Please try again with new parameters.");
        }
        try {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Create new Employee", description = "Create new Employee, accepts @RequestBody if valid", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created Employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "500", description = "Employees not created",
                    content = @Content)})
    @PostMapping("/employee/create")
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
        try {
            Employee employee1 = employeeService
                    .createEmployee(new Employee(
                            employee.getName(),
                            employee.getRole(),
                            employee.getEmail(),
                            employee.getStatus(),
                            employee.getDateOfBirth(),
                            employee.getCareerGoal()
                            ));

            return new ResponseEntity<>(employee1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Create new Employee", description = "Create new Employee, accepts @RequestBody if valid", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created Employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDto.class))}),
            @ApiResponse(responseCode = "500", description = "Employees not created",
                    content = @Content)})
    @PostMapping("/employee/create/dto")
    public ResponseEntity<EmployeeDto> saveEmployeeAsDto(@Valid @RequestBody EmployeeDto employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employeeService.save(employee);
        EmployeeDto employeeDTO1 = employeeMapper.toDto(employee);
        try {
//            Employee employee1 = employeeService
//                    .createEmployee(new Employee(employee.getName(), employee.getRole(), employee.getEmail(), employee.getStatus()));
            return new ResponseEntity<>(employeeDTO1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update Employee by Id", description = "Update existing Employee by Id, accepts @RequestBody if valid, uses PathVariable for id", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "500", description = "Employees not updated",
                    content = @Content)})
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody @Valid Employee employee, @PathVariable Long id) {
        Optional<Employee> employeeOptional = employeeService.findEmployeeById(id);
        if (employeeOptional.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id: %d not found".formatted(id));
        }
        try {
            return new ResponseEntity<>(employeeService.updateEmployeeById(employee, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Employee by Id", description = "Delete existing Employee by Id, accepts  if valid, uses PathVariable for id", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "500", description = "Employees not deleted",
                    content = @Content)})
    @DeleteMapping("/employee/delete/{id}") // update with Response Entity to return response
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
    }



    @Operation(summary = "Assign Task to Employee", description = "Assign task to employee, uses PathVariable for id", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task assigned to Employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "500", description = "Task not assigned",
                    content = @Content)})
    @PutMapping("/{employeeId}/employee/{taskId}")
    public ResponseEntity<Employee> assignEmployeeTask(@PathVariable Long employeeId, @PathVariable Long taskId) {
        Optional<Employee> employeeOptional = employeeService.findEmployeeById(employeeId);
        Optional<Task> taskOptional = taskRepository.findTaskById(taskId);
        if (employeeOptional.isEmpty() || taskOptional.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id: %d or task id: %d not found".formatted(employeeId, taskId));
        }
        Task task = taskOptional.get();
        Employee employee = employeeOptional.get();
        employee.addTask(taskOptional.get());
        employeeService.save(employee);
        try {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(employee, HttpStatus.NOT_FOUND);
        }
    }

    // remove task
    @Operation(summary = "Remove Task from Employee", description = "Remove task from employee, uses PathVariable for id", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task removed from Employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "500", description = "Task not removed",
                    content = @Content)})
    @PutMapping("/{employeeId}/employee/{taskId}/remove")
    public ResponseEntity<Employee> removeTaskFromEmployee(@PathVariable Long employeeId, @PathVariable Long taskId) {
        Optional<Employee> employeeOptional = employeeService.findEmployeeById(employeeId);
        Optional<Task> taskOptional = taskRepository.findTaskById(taskId);
        Task task = taskOptional.get();
        if (employeeOptional.isEmpty() || taskOptional.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id: %d or task with id: %d not found".formatted(employeeId, taskId));
        }
        Employee employee = employeeOptional.get();
        employee.removeTask(taskId);
        employeeService.save(employee);
        try {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(employee, HttpStatus.NOT_FOUND);
        }

    }


}
