package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDTO;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.exceptions.EmployeeNotFoundException;
import com.laurencetuchin.employeesystemapi.mappers.EmployeeMapper;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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

    public List<Employee> findByNameIgnoreCaseContains(String partialName){
        return employeeRepository.findByNameIgnoreCaseContains(partialName);
    }

    public List<Employee> findByRoleIgnoreCaseContains(String role){
        return employeeRepository.findByRoleIgnoreCaseContains(role);
    }

    public List<Employee> findCurrentlyEmployedEmployees(boolean isCurrentlyEmployedAtCompany){
        return employeeRepository.findByIsCurrentlyWorkingAtCompany(isCurrentlyEmployedAtCompany);
    }

    public List<Employee> findEmployeeByNameAndRole(String partialName, String role){
        return employeeRepository.findEmployeeByNameAndRole(partialName, role);
    }

    public List<Employee> findEmployeeByNameOrRole(String partialName, String role){
        return employeeRepository.findEmployeeByNameOrRole(partialName,role);
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

    public Optional<Employee> getEmployeeDTOById(Long id){
        return employeeRepository.findById(id);
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
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

    public Employee updateEmployeeById(@NotNull Employee employee, Long id){
        Optional<Employee> employeeExists = employeeRepository.findById(id);
        Employee employee1 = employeeExists.get();
        if (!employeeExists.isPresent()){
            throw new EmployeeNotFoundException("Employee with id: " + id + " does not exist");
        } else {
            employee1.setName(employee.getName());
            employee1.setRole(employee.getRole());
            employee1.setCurrentlyWorkingAtCompany(employee.isCurrentlyWorkingAtCompany());
            employeeRepository.save(employee1);
        }
        return employee1;
    }


//    @Query("select e from Employee e where e.employeeTasks = ?1")
//    public List<Employee> findByEmployeeTasks(Task employeeTasks) {
//        return employeeRepository.findByEmployeeTasks(employeeTasks);
//    }

//    @Query("select e from Employee e inner join e.employeeTasks employeeTasks where employeeTasks.name = :name")
//    public List<Employee> findByEmployeeTasks_Name(String name) {
//        return employeeRepository.findByEmployeeTasks_Name(name);
//    }

//    public Task addTaskToEmployee(Task task){
//
//    }



}

