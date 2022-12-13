package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.ProjectStatus;
import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.repositories.TaskRepository;
import com.laurencetuchin.employeesystemapi.services.ProjectService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaskRepository taskRepository;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping("/find/name/")
    public List<Project> findProjectByName(@RequestParam String name) {
        return service.findProjectByName(name);
    }

    @GetMapping("/find/status/")
    public List<Project> findProjectByStatus(@RequestParam ProjectStatus status) {
        return service.findProjectByStatus(status);
    }


    @GetMapping("/all")
    public List<Project> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Project> findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping("/save")
    public Project saveProject(@RequestBody Project project) {
        return service.saveProject(project);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProject(@PathVariable("id") Long id) {
        service.deleteProject(id);
    }

    @PutMapping("/update/{id}")
    public Project updateProjectById(@RequestBody @NotNull Project project,@PathVariable Long id) {
        return service.updateProjectById(project, id);
    }

    @GetMapping("/find/employee/")
    public List<Project> findByEmployee_NameAllIgnoreCase(@RequestParam String name) {
        return service.findByEmployee_NameAllIgnoreCase(name);
    }

    @GetMapping("/status/active")
    public List<Project> findByStatusActive() {
        return service.findProjectByStatus(ProjectStatus.PENDING);
    }

    @PutMapping("/{projectId}/employee/{employeeId}")
    public Project assignProjectToEmployee(@PathVariable Long projectId, @PathVariable Long employeeId){
        Optional<Project> project = service.findById(projectId);
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        project.get().setEmployee(employee.get());
        service.saveProject(project.get());
        return project.get();
    }

    @PutMapping("/{projectId}/employee/{employeeId}/remove")
    public Project removeProjectFromEmployee(@PathVariable Long projectId, @PathVariable Long employeeId){
        Optional<Project> project = service.findById(projectId);
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        project.get().setEmployee(null);
        return project.get();
    }

    @PutMapping("/{projectId}/task/{taskId}")
    public Project assignTaskToProject(@PathVariable Long projectId, @PathVariable Long taskId){
        Optional<Project> projectOptional = service.findById(projectId);
        Optional<Task> taskOptional = taskRepository.findTaskById(taskId);

        projectOptional.get().addTask(taskOptional.get());
        service.saveProject(projectOptional.get());
        return projectOptional.get();
    }

}
