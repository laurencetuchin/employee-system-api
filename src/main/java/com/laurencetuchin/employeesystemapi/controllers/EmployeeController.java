package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDTO;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

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

    @GetMapping("/employees")
    public List<EmployeeDTO> getAllEmployeesDTO() {
        return employeeService.getAllEmployees().stream()
                .map(EmployeeDTO::new)
                .collect(Collectors.toList());
    }




    @GetMapping("/search/{partialName}")
    List<Employee> findByNameIgnoreCaseContains(@PathVariable String partialName){
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
    public void deleteEmployee(Long id){
        employeeService.deleteEmployeeById(id);
    }


//    @PutMapping("/employee/update/{id}")
//    public void updateEmployee(Long id){
//        employeeService.
//    }

}
