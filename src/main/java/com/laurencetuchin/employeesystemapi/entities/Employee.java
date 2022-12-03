package com.laurencetuchin.employeesystemapi.entities;

import com.laurencetuchin.employeesystemapi.seedData.RegexEmailCompliant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity // @Table not needed for object storage
//@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 1, max = 10)
    @NotNull
    private String name;

    @Column(name = "role")
    @NotNull
    private String role;

    @Column(name = "email")
    @Email(message = "Email must be valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
//    @Email(message = "Email must be valid", regexp = RegexEmailCompliant  );
    private String email;

    @Column(name = "employment_status")
    private boolean isCurrentlyWorkingAtCompany = true;

//    orphanRemoval = true,
    @OneToMany( mappedBy = "employee", fetch = FetchType.EAGER)
    private List<Project> projects;

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


