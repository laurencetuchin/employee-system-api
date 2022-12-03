package com.laurencetuchin.employeesystemapi.entities;

import net.bytebuddy.asm.Advice;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

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

    @Column(name = "startDate") // defaults Project start time to now
    private LocalDateTime startDate = LocalDateTime.now();

    @Column(name = "endDate") // defaults Project end time to 7 days from now
    private LocalDateTime endDate = LocalDateTime.now().plusDays(7);


    @Column(name = "timeRemaining")
    private Long timeRemaining = Duration.between(endDate,startDate).toMillis();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
    private Employee employee;

    public Project() {
    }

    public Project(String name, String assignedEmployees, ProjectStatus status) {
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

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
