package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.ProjectStatus;
import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.exceptions.EmployeeNotFoundException;
import com.laurencetuchin.employeesystemapi.exceptions.ProjectNotFoundException;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.repositories.TaskRepository;
import com.laurencetuchin.employeesystemapi.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins = "http://localhost:8081/*")
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

    @Operation(summary = "Find Project by Name", description = "Find Project by Name query, case insensitive e.g. ManchEster, manchester, MANCHESTER", tags = "Project" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content)})
    @GetMapping("/find/name/")
    public ResponseEntity<List<Project>> findProjectByName(@RequestParam String name) {
        List<Project> project = service.findProjectByName(name);
        if (project.isEmpty()){
            throw new ProjectNotFoundException("Project with name: " + name + " not found");
        } try {
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (ProjectNotFoundException e){
            return new ResponseEntity<>(project, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Find Project by Status", description = "Find Project by Status, must match enum e.g. pending, complete, notready", tags = "Project" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content)})
    @GetMapping("/find/status/")
    public ResponseEntity<List<Project>> findProjectByStatus(@RequestParam ProjectStatus status) {
        List<Project> project = service.findProjectByStatus(status);
        if (project.isEmpty()){
            throw new ProjectNotFoundException("Project with status: " + status + " not found");
        }
        try {
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<>(project, HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Find all Projects", description = "Find Project all Projects", tags = "Project" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "404", description = "No Projects found",
                    content = @Content)})
    @GetMapping("/all")
    public ResponseEntity<List<Project>> findAll() {
        List<Project> projectList = service.findAll();
        if (projectList.isEmpty()){
            throw new ProjectNotFoundException("No Projects found");
        }
        try {
            return new ResponseEntity<>(projectList, HttpStatus.OK);
        } catch (ProjectNotFoundException e){
            return new ResponseEntity<>(projectList,HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Find Project by Id", description = "Find Project by Id, Searches with Id as PathVariable", tags = "Project" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "404", description = "No Projects found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Project>> findById(@PathVariable("id") Long id) {

        Optional<Project> project = service.findById(id);
        if (project.isEmpty()){
            throw new ProjectNotFoundException("Project with id: " + id + " not found");
        }
        try {
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (ProjectNotFoundException e){
            return new ResponseEntity<>(project, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public Project saveProject(@Valid @RequestBody Project project) {
        return service.saveProject(project);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProject(@PathVariable("id") Long id) {
        service.deleteProject(id);
    }

    @PutMapping("/update/{id}")
    public Project updateProjectById(@Valid @RequestBody Project project, @PathVariable Long id) {
        return service.updateProjectById(project, id);
    }

    @GetMapping("/find/employee/")
    public List<Project> findByEmployee_NameAllIgnoreCase(@RequestParam String name) {
        return service.findByEmployee_NameAllIgnoreCase(name);
    }

    @GetMapping("/status/active")
    public List<Project> findByStatusActive() {
        return service.findProjectByStatus(ProjectStatus.pending);
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
//        project.get().removeEmployee(employeeId);
        project.get().setEmployee(null); // is null ok bc only one employee assigned to project
        service.saveProject(project.get());
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
