package com.laurencetuchin.employeesystemapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "priority")
    private TaskPriority priority;

    @Column(name = "startDate") // add constraints for start date must be before end date
    private LocalDateTime startDate;

    @Column(name = "endDate")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @JsonIgnoreProperties("tasks")
    private Project project;

    @ManyToMany
    @JoinTable(
            name = "employee_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")

    )
    private Set<Employee> employeesAssignedTask = new HashSet<>();
//
//    @ManyToMany(mappedBy = "employees")
//    private Set<Employee> employeeSet = new HashSet<>();


//    public Project getProject() {
//        return project;
//    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Autowired
    public Task(String name, String description, TaskStatus status, TaskPriority priority, LocalDateTime startDate, LocalDateTime endDate, Project project) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.project = project;
    }

    public Task() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
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
//
//    public Project getProject() {
//        return project;
//    }

    public Set<Employee> getEmployeesAssignedTask() {
        return employeesAssignedTask;
    }

    public void setEmployeesAssignedTask(Set<Employee> employeesAssignedTask) {
        this.employeesAssignedTask = employeesAssignedTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
