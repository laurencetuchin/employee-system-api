package com.laurencetuchin.employeesystemapi.controllers;

//import com.laurencetuchin.employeesystemapi.dto.EmployeeDTO;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.seedData.DataLoader;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api") // Need to add in API versioning
//@CrossOrigin(origins = "http://localhost:8081")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home() {
        return "You are home";
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

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        Optional<Employee> employeeIfExists = employeeService.findEmployeeById(id);

        if (employeeIfExists.isPresent()) {
            return new ResponseEntity<>(employeeIfExists.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @GetMapping("/test")
    @ResponseBody
    public String testMethod(){
        return "test method";
    }

    // Returns employees that currently work at company

    // Search result based on employment status
    @GetMapping("/employment{result}")
    public List<Employee> getCurrentlyEmployedEmployees(@RequestParam boolean result) {
//            return employeeService.findCurrentlyEmployedEmployees(result);
        // add in error handling

        try {
            return employeeService.findCurrentlyEmployedEmployees(result);
        } catch (IllegalArgumentException e) {
//             return "Error " + e;
//            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            throw new IllegalArgumentException(e);
        }


    }



    @GetMapping("/search{partialName}")
//    @ResponseBody
    List<Employee> findByNameIgnoreCaseContains(@RequestParam String partialName){
        return employeeService.findByNameIgnoreCaseContains(partialName);
    }



    @PostMapping("/employee/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee employee1 = employeeService
                    .addNewEmployee(new Employee(employee.getName(), employee.getRole()));
            return new ResponseEntity<>(employee1, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id) {
        Optional<Employee> employeeIfExists = employeeService.findEmployeeById(id);


        if (employeeIfExists.isPresent()) {
            Employee employee1 = employeeIfExists.get();
            employee1.setName(employee1.getName());
            employee1.setRole(employee1.getRole());
            return new ResponseEntity<>(employeeService.save(employee1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/employee/delete/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
    }


//    @PutMapping("/employee/update/{id}")
//    public void updateEmployee(Long id){
//        employeeService.
//    }

}
