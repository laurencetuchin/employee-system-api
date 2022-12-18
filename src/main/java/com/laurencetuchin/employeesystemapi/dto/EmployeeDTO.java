package com.laurencetuchin.employeesystemapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laurencetuchin.employeesystemapi.entities.EmploymentStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class EmployeeDTO {


    private Long id;


    @Size(min = 1, max = 10)
    @NotNull @NotBlank
    private String name;

    @JsonProperty("UserRole")
    private String role;

    @Email(message = "Email must be valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;

    @JsonProperty("EmploymentStatus")
    @NotNull
    private EmploymentStatus status;

    @NotBlank
    @DateTimeFormat
    private Date dateOfBirth;

    @NotBlank
    @Size(min = 1, max = 100)
    private String careerGoal;


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


    public EmployeeDTO(Long id, String name, String role, String email, EmploymentStatus status, Date dateOfBirth, String careerGoal) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.status = status;
        this.dateOfBirth = dateOfBirth;
        this.careerGoal = careerGoal;
    }
}
