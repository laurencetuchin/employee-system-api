package com.laurencetuchin.employeesystemapi.entities;

import javax.persistence.*;

@Entity
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "assignedEmployees")
    private String assignedEmployees;
    @Column(name = "status")
    private String status;

    public Project(String name, String assignedEmployees, String status) {
        this.name = name;
        this.assignedEmployees = assignedEmployees;
        this.status = status;
    }


}
