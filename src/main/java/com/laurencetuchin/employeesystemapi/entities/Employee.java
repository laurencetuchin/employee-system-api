package com.laurencetuchin.employeesystemapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
 // @Table not needed for object storage
//@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 1, max = 10)
//    @NotNull @NotBlank
    private String name;

    @Column(name = "role")
//    @NotNull @NotBlank
    private String role;

    @Column(name = "email")
    @Email(message = "Email must be valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
//    @Email(message = "Email must be valid", regexp = RegexEmailCompliant  );
    private String email;

    @Column(name = "employment_status")
    private boolean isCurrentlyWorkingAtCompany = true;

//    orphanRemoval = true,
    @JsonIgnore
    @OneToMany( mappedBy = "employee")
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
    public Employee(String name, String role, boolean isCurrentlyWorkingAtCompany) {
        this.name = name;
        this.role = role;
        this.isCurrentlyWorkingAtCompany = isCurrentlyWorkingAtCompany;
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

    public boolean isCurrentlyWorkingAtCompany() {
        return isCurrentlyWorkingAtCompany;
    }

    public void setCurrentlyWorkingAtCompany(boolean currentlyWorkingAtCompany) {
        isCurrentlyWorkingAtCompany = currentlyWorkingAtCompany;
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
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", isCurrentlyWorkingAtCompany=" + isCurrentlyWorkingAtCompany +
                '}';
    }
}


