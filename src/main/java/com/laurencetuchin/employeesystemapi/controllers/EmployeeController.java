package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String home() {
        return "You are home";
    }


    @GetMapping("/api/search/{partialName}")
    List<Employee> findByNameIgnoreCaseContains(@PathVariable String partialName){
        return employeeService.findByNameIgnoreCaseContains(partialName);
    }


    @PostMapping("/api/employee/create")
    public void addNewEmployee(Employee employee){
        employeeService.addNewEmployee(employee);
    }



}
