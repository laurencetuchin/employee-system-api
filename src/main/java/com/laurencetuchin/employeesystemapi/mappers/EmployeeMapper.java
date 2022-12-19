package com.laurencetuchin.employeesystemapi.mappers;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDto;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeMapper {

    @Autowired
    private EmployeeService employeeService;

    public EmployeeDto toDto(@NotNull Employee employee) {
        EmployeeDto employeeDTO = new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getRole(),
                employee.getEmail(),
                employee.getStatus()
        );
        return employeeDTO;
    }
    public Employee toEntity(@NotNull EmployeeDto employeeDto) {
        Employee employee = new Employee(
                employeeDto.getName(),
                employeeDto.getRole(),
                employeeDto.getEmail(),
                employeeDto.getStatus()
        );
        return employee;
    }

}
