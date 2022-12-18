package com.laurencetuchin.employeesystemapi.mappers;

import com.laurencetuchin.employeesystemapi.dto.EmployeeDto;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class EmployeeMapper {

    @Autowired
    private EmployeeService employeeService;

    public EmployeeDto toDto(Employee employee) {
        EmployeeDto employeeDTO = new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getRole(),
                employee.getEmail(),
                employee.getStatus(),
                employee.getDateOfBirth(),
                employee.getCareerGoal()
        );
      //  EmployeeDTO employeeDTO = new EmployeeDTO();

      //   employeeDTO.setId(employee.getId());
      //  employeeDTO.setName(employee.getName());
      //  employeeDTO.setRole(employee.getRole());

        return employeeDTO;
    }

//    @Autowired
    public Employee toEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee(
                employeeDto.getName(),
                employeeDto.getRole(),
                employeeDto.getEmail(),
                employeeDto.getStatus(),
                employeeDto.getDateOfBirth(),
                employeeDto.getCareerGoal()

        );
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
