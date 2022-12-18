package com.laurencetuchin.employeesystemapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laurencetuchin.employeesystemapi.entities.EmploymentStatus;

public class EmployeeDto {


    private Long id;

    private String name;

    @JsonProperty("User role")
    private String role;

    private String email;

    @JsonProperty("EmploymentStatus")
    private EmploymentStatus status;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmploymentStatus getStatus() {
        return status;
    }

    public void setStatus(EmploymentStatus status) {
        this.status = status;
    }

    public EmployeeDto(Long id, String name, String role, String email, EmploymentStatus status) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" + "name='" + name + '\'' + ", role='" + role + '\'' + ", email='" + email + '\'' + ", status=" + status + '}';
    }
}
