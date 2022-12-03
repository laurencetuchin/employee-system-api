package com.laurencetuchin.employeesystemapi.entities;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
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

    // need to map to Employee
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

    @Autowired
    public Project(Long id, String name, String assignedEmployees, ProjectStatus status, Employee employee) {
        this.id = id;
        this.name = name;
        this.assignedEmployees = assignedEmployees;
        this.status = status;
        this.employee = employee;
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


    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    // Need to check time formatting
    public Long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(Long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}
