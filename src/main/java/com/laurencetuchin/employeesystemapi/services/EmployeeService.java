package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDTO;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.exceptions.EmployeeNotFoundException;
import com.laurencetuchin.employeesystemapi.mappers.EmployeeMapper;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findByName(String partialName){
        return employeeRepository.findByNameIgnoreCaseContains(partialName);
    }

    public List<Employee> findByRole(String role){
        return employeeRepository.findByRoleIgnoreCaseContains(role);
    }

    public List<Employee> findByEmploymentStatus(boolean isCurrentlyEmployedAtCompany){
        return employeeRepository.findByIsCurrentlyWorkingAtCompany(isCurrentlyEmployedAtCompany);
    }

    public List<Employee> findEmployeeByNameAndRole(String partialName, String role){
        return employeeRepository.findEmployeeByNameAndRole(partialName, role);
    }

    public List<Employee> findEmployeeByNameOrRole(String partialName, String role){
        return employeeRepository.findEmployeeByNameOrRoleAllIgnoreCase(partialName,role);
    }


    public Optional<Employee> findEmployeeById(Long id){
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    // Add employee DTO list
    public List<EmployeeDTO> getAllEmployeesDTO(){
        return getAllEmployees()
                .stream()
                .map(employee -> employeeMapper.toDto(employee))
                .collect(Collectors.toList());
    }


    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeById(Long id){
        boolean employeeExists = employeeRepository.existsById(id);
        if (!employeeExists) {
            throw new IllegalStateException("Employee with id" + id + "does not exist");

        }
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployeeById(@NotNull Employee employee, Long id){
        Optional<Employee> employeeExists = employeeRepository.findById(id);
        Employee employee1 = employeeExists.get();
        if (!employeeExists.isPresent()){
            throw new EmployeeNotFoundException("Employee with id: " + id + " does not exist");
        } else {
            employee1.setName(employee.getName());
            employee1.setRole(employee.getRole());
            employee1.setEmploymentStatus(employee.getEmploymentStatus());
            employee1.setEmail(employee.getEmail());
            employeeRepository.save(employee1);
        }
        return employee1;
    }





}

