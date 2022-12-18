package com.laurencetuchin.employeesystemapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laurencetuchin.employeesystemapi.entities.EmploymentStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class CreateEmployeeDto {

    // allow only SuperUsers to see this Dto


    @JsonProperty(value = "Employee name")
    @Size(min = 1, max = 10)
    @NotNull
    @NotBlank
    private String name;

    @JsonProperty(value = "Company role")
    private String role;

    @Email(message = "Must be valid email")
    private String email;


    @JsonProperty(value = "Date of birth")
    private LocalDate dateOfBirth;

    @JsonProperty(value = "Employment status")
    @NotNull
    private EmploymentStatus status;


    @JsonProperty(value = "Career goal")
    @NotBlank
    @Size(min = 1, max = 100)
    private String careerGoal;




}
