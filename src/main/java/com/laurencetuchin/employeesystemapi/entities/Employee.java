package com.laurencetuchin.employeesystemapi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue
    Long id;

    private String name;

    private String role;

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }
}
