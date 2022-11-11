package com.laurencetuchin.employeesystemapi.mappers;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDTO;
import com.laurencetuchin.employeesystemapi.entities.Employee;

public class EmployeeMapper {


    public EmployeeDTO toDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO(employee.getId(), employee.getName(), employee.getRole());
      //  EmployeeDTO employeeDTO = new EmployeeDTO();

      //   employeeDTO.setId(employee.getId());
      //  employeeDTO.setName(employee.getName());
      //  employeeDTO.setRole(employee.getRole());

        return employeeDTO;
    }

    public Employee toEntity(EmployeeDTO employeeDto) {
        Employee employee = new Employee(employeeDto.getName(), employeeDto.getRole());
        //  EmployeeDTO employeeDTO = new EmployeeDTO(employee.getId(), employee.getName(), employee.getRole());
     //   EmployeeDTO employeeDTO = new EmployeeDTO();

     //   employeeDTO.setId(employee.getId());
     //   employeeDTO.setName(employee.getName());
    //    employeeDTO.setRole(employee.getRole());

        return employee;
    }

}
