package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDTO;
import com.laurencetuchin.employeesystemapi.dto.ErrorDTO;
import com.laurencetuchin.employeesystemapi.entities.Employee;
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
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee") // Need to add in API versioning
//@CrossOrigin(origins = "http://localhost:8081")
@Validated
@CrossOrigin(origins = "http://localhost:8081/*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TaskRepository taskRepository;

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeMapper employeeMapper = new EmployeeMapper();

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
    @Operation(summary = "Get Employee by Id", description = "Get an Employee by Id", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Employee",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found",
            content = @Content)})
    @GetMapping("/employee/{id}")
    public ResponseEntity getEmployeeById(@PathVariable("id") Long id) {
        Optional<Employee> employeeIfExists = employeeService.findEmployeeById(id);
        if (employeeIfExists.isEmpty()){
            throw new EmployeeNotFoundException("Employee with id: " + id + " not found");
        }
        try {
            if (employeeIfExists.isPresent()) {

                return ResponseEntity.status( HttpStatus.OK).body(employeeIfExists.get());
//                return new ResponseEntity<>(employeeIfExists.get(), HttpStatus.OK);
            }
        } catch (EmployeeNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Employee Found");
//            throw new EmployeeNotFoundException("Employee not found");
        }
        return new ResponseEntity<>(employeeIfExists.get(),HttpStatus.FOUND);
    }



    // Return Employee DTO for client
//
//    @GetMapping("/employees")
//    @ResponseBody
//    public List<EmployeeDTO> getAllEmployeesDTO() {
//        return employeeService.getAllEmployees().stream()
//                .map(EmployeeDTO::new)
//                .collect(Collectors.toList());
//    }


    @GetMapping("/employees/all")
    @ResponseBody
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }


    @GetMapping("/employees/dto")
    public List<EmployeeDTO> getAllEmployeesDTO() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees()
                .stream().map(employee -> employeeMapper.toDto(employee))
                .collect(Collectors.toList());
        return employees;
    }

    // Search result based on employment status
    @GetMapping("/employment{result}")
    public ResponseEntity<List<Employee>> getCurrentlyEmployedEmployees(@RequestParam boolean result) {
//            return employeeService.findCurrentlyEmployedEmployees(result);
        // add in error handling
        try {
            return new ResponseEntity<>(employeeService.findCurrentlyEmployedEmployees(result), HttpStatus.FOUND);
        } catch (HttpServerErrorException.InternalServerError e) {
//             return "Error " + e;
//            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

//    @GetMapping("/exception/{exception_id}")
//    public void getSpecificException(@PathVariable("exception_id") String pException){
//        if ("not_found".equals(pException)){
//            throw new EmployeeNotFoundException("employee not found");
//        } else if ("bad_arguments".equals(pException)){
//            throw new BadArgumentsException("bad arguments");
//        } else {
//            throw new InternalException("internal error");
//        }
//    }




    // add handler for no result
    // result is present?
    @GetMapping("/search{partialName}")
//    @ResponseBody
    ResponseEntity<List<Employee>> findByNameIgnoreCaseContains(@RequestParam String partialName){
        Boolean noResult = employeeService.findByNameIgnoreCaseContains(partialName).isEmpty();
        if (noResult){
            throw new EmployeeNotFoundException("Employee with name: " + partialName + " not found");
        }
        try {
            return new ResponseEntity<>(employeeService.findByNameIgnoreCaseContains(partialName), HttpStatus.FOUND);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/role{role}") // example api/search/role?role=Delivery
//    @ResponseBody
    ResponseEntity<List<Employee>> findByRoleIgnoreCaseContains(@RequestParam String role){
        boolean noResult = employeeService.findByRoleIgnoreCaseContains(role).isEmpty();
        if (noResult){
            throw new EmployeeNotFoundException("No employee with role: " + role + " found. Please try again with a different role");
        }
        try {
            return new ResponseEntity<>(employeeService.findByRoleIgnoreCaseContains(role), HttpStatus.FOUND);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/partialNameAndRole{partialName}") // pushes URL string like ?partialName=example&role=example
//    @ResponseBody
    ResponseEntity<List<Employee>> findEmployeeByNameAndRole(@RequestParam String partialName, @RequestParam String role){
        boolean noPartialNameOrRoleResult = employeeService.findEmployeeByNameAndRole(partialName,role).isEmpty();
        if (noPartialNameOrRoleResult){
            throw new EmployeeNotFoundException("Employee with name: " + partialName + " or role: " + role + " not found. Please try again with new parameters.");
        }
        try {
            System.out.println("name is: " + partialName + "role is : " + role);

            return new ResponseEntity<>(employeeService.findEmployeeByNameAndRole(partialName,role), HttpStatus.FOUND);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/employee/create")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        try {
            Employee employee1 = employeeService
                    .addNewEmployee(new Employee(employee.getName(), employee.getRole()));
            return new ResponseEntity<>(employee1, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody @Valid Employee employee,@PathVariable Long id) {
        Optional<Employee> employeeExists = employeeService.findEmployeeById(id);
//        Boolean employeeResult = employeeService.findEmployeeById(id).isEmpty();
        if (employee == null){
            throw new EmployeeNotFoundException("Please pass employee information in request body");
        }
        try {
            return new ResponseEntity<>(employeeService.updateEmployeeById(employee, id),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

//    @PutMapping("/employee/{id}")
//    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id) {
//        Optional<Employee> employeeIfExists = employeeService.findEmployeeById(id);
//
//
//        if (employeeIfExists.isPresent()) {
//            Employee employee1 = employeeIfExists.get();
//            employee1.setName(employee1.getName());
//            employee1.setRole(employee1.getRole());
//            employeeService.save(employee1);
//            return new ResponseEntity<>(employeeService.updateEmployeeById(employee1), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/employee/delete/{id}") // update with Response Entity to return response
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
    }


//    @PutMapping("/employee/update/{id}")
//    public void updateEmployee(Long id){
//        employeeService.
//    }


//    @GetMapping("/employee/tasks")
//    @Query("select e from Employee e where e.employeeTasks = ?1")
//    public List<Employee> findByEmployeeTasks(@RequestParam Task employeeTasks) {
//        return employeeService.findByEmployeeTasks(employeeTasks);
//    }

//    @GetMapping("/employee/{id}/task/{named}")
//    @Query("select e from Employee e inner join e.employeeTasks employeeTasks where employeeTasks.name = :name")
//    public List<Employee> findByEmployeeTasks_Name(@RequestParam String name, @PathVariable Long id) {
//        return employeeService.findByEmployeeTasks_Name(name);
//    }


    @PutMapping("/{employeeId}/employee/{taskId}")
    public Employee assignEmployeeTask(@PathVariable Long employeeId, @PathVariable Long taskId){
        Optional<Employee> employeeExists = employeeService.findEmployeeById(employeeId);
        Optional<Task> taskOptional = taskRepository.findTaskById(taskId);
        Task task = taskOptional.get();
        employeeExists.get().addTask(taskOptional.get());
//        Set<Task> singleton = Collections.singleton(task);
//        employeeExists.get().setTasks(singleton);
        employeeService.save(employeeExists.get());
        return employeeExists.get();
//        if (!employeeExists.isPresent()){
//            throw new EmployeeNotFoundException("Employee with id: " + employeeId + "does not exist");
//        } else {
//            employeeExists.get().addTask(task);
//        }

    }

    // remove task
    @PutMapping("/{employeeId}/employee/{taskId}/remove")
    public Employee removeTaskFromEmployee(@PathVariable Long employeeId, @PathVariable Long taskId){
        Optional<Employee> employeeExists = employeeService.findEmployeeById(employeeId);
        Optional<Task> taskOptional = taskRepository.findTaskById(taskId);
        Task task = taskOptional.get();
//        employeeExists.get().setTasks(taskOptional.get().setEmployees(null)); // remove task on id
        employeeExists.get().removeTask(taskId);
        employeeService.save(employeeExists.get());
        return employeeExists.get();
    }


}
