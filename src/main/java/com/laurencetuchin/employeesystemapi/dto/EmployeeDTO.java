package com.laurencetuchin.employeesystemapi.dto;

import com.laurencetuchin.employeesystemapi.entities.Employee;

public class EmployeeDTO {

    private final Employee employee;

    public EmployeeDTO(Employee employee) {
        this.employee = employee;
    }

    public String getEmployeeName() {
        return this.employee.getName();
    }

    public String getEmployeeRole(){
        return this.employee.getRole();
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "employee=" + employee +
                '}';
    }
}
