package com.laurencetuchin.employeesystemapi.entities;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity // @Table not needed for object storage
//@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @Column(name = "role")
    @NotNull
    private String role;

    @Column(name = "employment_status")
    private boolean isCurrentlyWorkingAtCompany = true;


    public Employee() {
    }

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }

    @Autowired
    public Employee(String name, String role, boolean isCurrentlyWorkingAtCompany) {
        this.name = name;
        this.role = role;
        this.isCurrentlyWorkingAtCompany = isCurrentlyWorkingAtCompany;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isCurrentlyWorkingAtCompany() {
        return isCurrentlyWorkingAtCompany;
    }

    public void setCurrentlyWorkingAtCompany(boolean currentlyWorkingAtCompany) {
        isCurrentlyWorkingAtCompany = currentlyWorkingAtCompany;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", isCurrentlyWorkingAtCompany=" + isCurrentlyWorkingAtCompany +
                '}';
    }
}


