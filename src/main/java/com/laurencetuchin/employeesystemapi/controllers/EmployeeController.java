package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String home() {
        return "You are home";
    }


    @GetMapping("/search/{partialName}")
    List<Employee> findByNameIgnoreCaseContains(@PathVariable String partialName){
        return employeeService.findByNameIgnoreCaseContains(partialName);
    }


    @PostMapping("/employee/create")
    public void addNewEmployee(Employee employee){
        employeeService.addNewEmployee(employee);
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
