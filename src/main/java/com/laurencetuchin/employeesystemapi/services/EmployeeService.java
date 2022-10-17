package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDTO;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findByNameIgnoreCaseContains(String partialName){
        return employeeRepository.findByNameIgnoreCaseContains(partialName);
    }

    public Optional<Employee> findEmployeeById(Long id){
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    // Add employee DTO list 
    public Optional<Employee> getEmployeeDTOById(Long id){
        return employeeRepository.findById(id);
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }


    public Optional<Employee> getEmployeeById(Long id){
        return employeeRepository.findById(id);
    }

    // add check for already exists
    public Employee addNewEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeById(Long id){
        boolean employeeExists = employeeRepository.existsById(id);
        if (!employeeExists) {
            throw new IllegalStateException("Employee with id" + id + "does not exist");

        }
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployeeById(Employee employee){
        boolean employeeExists = employeeRepository.existsById(employee.getId());
        if (!employeeExists){
            throw new IllegalStateException("Employee with id" + employee.getId() + "does not exist");
        } else {

        employeeRepository.save(employee);
        }
        return employee;
    }



}
