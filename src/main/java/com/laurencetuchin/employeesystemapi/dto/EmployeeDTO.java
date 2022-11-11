package com.laurencetuchin.employeesystemapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import java.util.List;

public class EmployeeDTO {


    private Long id;

    private String name;

    @JsonProperty("UserRole")
    private String role;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }



    public EmployeeDTO(Long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

}
