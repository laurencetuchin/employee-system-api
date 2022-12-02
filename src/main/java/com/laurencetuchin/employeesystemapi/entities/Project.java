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
    private ProjectStatus status;

    public enum ProjectStatus {
        PENDING,
        COMPLETE,
        NOTREADY
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
    private Employee employee;

    public Project() {
    }

    public Project(String name, String assignedEmployees, String status) {
        this.name = name;
        this.assignedEmployees = assignedEmployees;
        this.status = status;
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

    public String getAssignedEmployees() {
        return assignedEmployees;
    }

    public void setAssignedEmployees(String assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
