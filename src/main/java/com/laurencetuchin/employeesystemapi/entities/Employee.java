package com.laurencetuchin.employeesystemapi.entities;

import javax.persistence.*;

@Entity
//@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "employed")
    private boolean isCurrentlyWorkingAtCompany;


    public Employee() {
    }

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
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

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", isCurrentlyWorkingAtCompany=" + isCurrentlyWorkingAtCompany +
                '}';
    }
}


