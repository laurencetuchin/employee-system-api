package com.laurencetuchin.employeesystemapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.executable.ValidateOnExecution;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull @NotBlank
    private String name;

    @Column(name = "description")
    @NotNull @NotBlank
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus status;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskPriority priority;

    @Column(name = "startDate") // add constraints for start date must be before end date
//    @FutureOrPresent
//    @Pattern(regexp = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$\n",message = "Start date must be in date format yyyy--mm-dd")
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
//    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime startDate = LocalDateTime.now();

    @Column(name = "endDate")
    @FutureOrPresent
    private LocalDateTime endDate = LocalDateTime.now().plusDays(7);

    @ManyToOne(fetch = FetchType.LAZY) // , optional = false
    @JoinColumn(name = "project_id") //, nullable = false
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE) // deletes Task if project is deleted
    private Project project;

    @JsonIgnore
    @ManyToMany(mappedBy = "tasks")
    private Set<Employee> employees = new HashSet<>();
//
//    @ManyToMany(mappedBy = "employees")
//    private Set<Employee> employeeSet = new HashSet<>();


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    @Autowired
    public Task(String name, String description, TaskStatus status, TaskPriority priority, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee){
        this.employees.add(employee);
        employee.getTasks().add(this);
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
//                ", project=" + project +
                ", employeesAssignedTask=" + employees +
                '}';
    }
}
