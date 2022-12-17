package com.laurencetuchin.employeesystemapi.mappers;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDTO;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class EmployeeMapper {

    @Autowired
    private EmployeeService employeeService;

    public EmployeeDTO toDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO(employee.getId(), employee.getName(), employee.getRole(), employee.getEmail(), employee.getStatus());
      //  EmployeeDTO employeeDTO = new EmployeeDTO();

      //   employeeDTO.setId(employee.getId());
      //  employeeDTO.setName(employee.getName());
      //  employeeDTO.setRole(employee.getRole());

        return employeeDTO;
    }

//    @Autowired
    public Employee toEntity(EmployeeDTO employeeDto) {
        Employee employee = new Employee(employeeDto.getName(), employeeDto.getRole(), employeeDto.getEmail(),employeeDto.getStatus());
        //  EmployeeDTO employeeDTO = new EmployeeDTO(employee.getId(), employee.getName(), employee.getRole());
     //   EmployeeDTO employeeDTO = new EmployeeDTO();
        // save Employee?
//        employeeService.save(employee);
     //   employeeDTO.setId(employee.getId());
     //   employeeDTO.setName(employee.getName());
    //    employeeDTO.setRole(employee.getRole());

        return employee;
    }

}
