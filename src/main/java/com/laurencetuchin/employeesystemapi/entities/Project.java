package com.laurencetuchin.employeesystemapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.EnumOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Entity
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotNull(message = "name must not be null")
    private String name;

    // need to map to Employee - may remove, field seems redundant
//    @Column(name = "assignedEmployees")
//    private String assignedEmployees;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProjectStatus status;

    @Column(name = "startDate") // defaults Project start time to now
    private LocalDateTime startDate = LocalDateTime.now();

    @Column(name = "endDate") // defaults Project end time to 7 days from now
    @FutureOrPresent
    private LocalDateTime endDate = LocalDateTime.now().plusDays(7);


    @Column(name = "timeRemaining")
    private Long timeRemaining = Duration.between(startDate,endDate).toMillis();

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

    @OneToMany(mappedBy = "project",fetch = FetchType.LAZY) // , orphanRemoval = true
    @JsonIgnore
    private Set<Task> task;

    public Set<Task> getTask() {
        return task;
    }

    // helper method to add Tasks to project
    public void addTask(Task task) {
        this.task.add(task);
        task.setProject(this);
    }
    // remove task method?


    public void setTask(Set<Task> task) {
        this.task = task;
    }

    public Project() {
    }

    @Autowired
    public Project(Long id, String name, ProjectStatus status) {
        this.id = id;
        this.name = name;
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

    public void removeEmployee(Long employeeId){
        Long employeeId1 = this.employee.getId();
        if (employeeId1 == employeeId){
            this.employee.setProjects(null);
            employee.setProjects(null);
        }
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



    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", timeRemaining=" + timeRemaining +
                '}';
    }
}
