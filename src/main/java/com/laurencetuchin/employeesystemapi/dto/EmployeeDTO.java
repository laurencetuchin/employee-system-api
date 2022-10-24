package com.laurencetuchin.employeesystemapi.dto;

import com.laurencetuchin.employeesystemapi.entities.Employee;

public class EmployeeDTO {

    private final Employee employee;

    public EmployeeDTO(Employee employee) {
        this.employee = employee;
    }

    public Long getEmployeeId() {
        return this.employee.getId();
    }

    public String getEmployeeName() {
        return this.employee.getName();
    }


    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "employee=" + employee +
                '}';
    }
}
