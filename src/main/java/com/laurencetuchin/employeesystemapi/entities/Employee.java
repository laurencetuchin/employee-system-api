package com.laurencetuchin.employeesystemapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
 // @Table not needed for object storage
//@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 1, max = 40)
    @NotNull @NotBlank
    private String name;

    @Column(name = "role")
    @NotNull @NotBlank(message = "role must not be blank")
    private String role;

    @Column(name = "email")
    @Email(message = "Email must be valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;

    @Column(name = "employment_status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private EmploymentStatus status;

    @Column(name = "dateOfBirth")
    @DateTimeFormat
    private LocalDate dateOfBirth;


    @Column(name = "careerGoal")
    @NotBlank
    @Size(min = 1, max = 100)
    private String careerGoal;

    @OneToMany( mappedBy = "employee")
//    @JsonManagedReference
//    @JsonIgnore
    @JsonBackReference
    private List<Project> projects;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "employee_tasks",
    joinColumns = @JoinColumn(name = "employee_id"),
    inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Set<Task> tasks = new HashSet<>();

    public Employee() {
    }

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }

    @Autowired
    public Employee(String name, String role, String email, EmploymentStatus status, LocalDate dateOfBirth, String careerGoal) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.status = status;
        this.dateOfBirth = dateOfBirth;
        this.careerGoal = careerGoal;
    }

    public Employee(Long id, String name, String role, String email, EmploymentStatus status) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.status = status;
    }

    // for EmployeeDto
    public Employee(String name, String role, String email, EmploymentStatus status) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmploymentStatus getStatus() {
        return status;
    }

    public void setStatus(EmploymentStatus status) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project){
        this.projects.add(project);
//        project.getEmployee().
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCareerGoal() {
        return careerGoal;
    }

    public void setCareerGoal(String careerGoal) {
        this.careerGoal = careerGoal;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task){
        this.tasks.add(task);
        task.getEmployees().add(this);
    }

    public void removeTask(Long taskId){
        Task task = this.tasks.stream().filter(t -> t.getId() == taskId).findFirst().orElse(null);
        if (task != null){
            this.tasks.remove(task);
            task.getEmployees().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", employmentStatus=" + status +
                '}';
    }
}


