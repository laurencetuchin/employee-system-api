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

    @Operation(summary = "Create Project", description = "Create Project, accepts RequestBody", tags = {"Project","Post"} )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Projects created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "400", description = "Project not created",
                    content = @Content)})
    @PostMapping("/save")
    public ResponseEntity<Project> saveProject(@Valid @RequestBody Project project) {
        Project saveProject = service.saveProject(project);
        try {
            return new ResponseEntity<>(saveProject, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Project by Id", description = "Delete Project by Id PathVariable", tags = {"Delete"} )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "400", description = "Project not deleted",
                    content = @Content)})
    @DeleteMapping("/delete/{id}")
    public void deleteProject(@PathVariable("id") Long id) {
        service.deleteProject(id);
    }

    @Operation(summary = "Update Project by Id", description = "Update Project by Id PathVariable", tags = {"Put"} )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "400", description = "Project not updated",
                    content = @Content)})
    @PutMapping("/update/{id}")
    public ResponseEntity<Project> updateProjectById(@Valid @RequestBody Project project, @PathVariable Long id) {
        Optional<Project> projectId = service.findById(id);
        Project updateProjectById = service.updateProjectById(project, id);
        if (projectId.isEmpty()){
            throw new ProjectNotFoundException("Project with id: " + id + " not found");
        }
        try {
            return new ResponseEntity<>(updateProjectById,HttpStatus.OK);
        } catch (ProjectNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Find Project by Employee", description = "Find Project by Employee, must have association between Project and Employee", tags = "Project" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content)})
    @GetMapping("/find/employee/")
    public ResponseEntity<List<Project>> findByEmployee_NameAllIgnoreCase(@RequestParam String name) {

        List<Project> employee = service.findByEmployee_NameAllIgnoreCase(name);
        if (employee.isEmpty()){
            throw new ProjectNotFoundException("Project with Employee name: " + employee + " not found");
        }
        try {
            return new ResponseEntity<>(employee,HttpStatus.OK);
        } catch (ProjectNotFoundException e){
            return new ResponseEntity<>(employee, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Find Active Projects", description = "Find Active Projects", tags = "Project" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content)})
    @GetMapping("/status/active")
    public ResponseEntity<List<Project>> findByStatusActive() {

        List<Project> activeProjects = service.findProjectByStatus(ProjectStatus.pending);
        if (activeProjects.isEmpty()){
            throw new ProjectNotFoundException("Projects with status: Active not found");
        } try {
            return new ResponseEntity<>(activeProjects, HttpStatus.OK);
        } catch (ProjectNotFoundException e){
            return new ResponseEntity<>(activeProjects, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Assign Project to Employee", description = "Assign Project to Employee", tags = "Project" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project assigned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "500", description = "Project not assigned",
                    content = @Content)})
    @PutMapping("/{projectId}/employee/{employeeId}")
    public ResponseEntity<Project> assignProjectToEmployee(@PathVariable Long projectId, @PathVariable Long employeeId){

        Optional<Project> project = service.findById(projectId);
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (project.isEmpty() && employee.isEmpty()){
            throw new ProjectNotFoundException("Project cannot be assigned because project: " + project + " or employee: " + employee + " is empty");
        }
        Project project1 = project.get();
                project1.setEmployee(employee.get());
        Project saveProject = service.saveProject(project1);
        try {
            return new ResponseEntity<>(project1, HttpStatus.OK);
        } catch (ProjectNotFoundException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Remove Project from Employee", description = "Remove Project from Employee", tags = "Project" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project removed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "500", description = "Project not removed",
                    content = @Content)})
    @PutMapping("/{projectId}/employee/{employeeId}/remove")
    public ResponseEntity<Project> removeProjectFromEmployee(@PathVariable Long projectId, @PathVariable Long employeeId){
        Optional<Project> project = service.findById(projectId);
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (project.isEmpty() || employee.isEmpty()){
            throw new ProjectNotFoundException("Project cannot be removed because project: " + project + " or employee: " + employee + " is empty");
        }
        Project project1 = project.get();
        project1.setEmployee(null); // is null ok bc only one employee assigned to project
        Project saveProject = service.saveProject(project1);
        try {
            return new ResponseEntity<>(project1, HttpStatus.OK);
        } catch (ProjectNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        project.get().removeEmployee(employeeId);
    }

    @Operation(summary = "Assign Task to Project", description = "Assign Task to Project", tags = "Project" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task assigned to Project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "500", description = "Task not assigned to Project",
                    content = @Content)})
    @PutMapping("/{projectId}/task/{taskId}")
    public ResponseEntity<Project> assignTaskToProject(@PathVariable Long projectId, @PathVariable Long taskId){
        Optional<Project> projectOptional = service.findById(projectId);
        Optional<Task> taskOptional = taskRepository.findTaskById(taskId);
        if (projectOptional.isEmpty() || taskOptional.isEmpty()){
            throw new ProjectNotFoundException("Project cannot be removed because project: " + projectId + " or task: " + taskId + " is empty");
        }
        Project project = projectOptional.get();
        Task task = taskOptional.get();
        try {
        project.addTask(task);
        Project saveProject = service.saveProject(project);
            return new ResponseEntity<>(project,HttpStatus.OK);
        } catch (ProjectNotFoundException e){
            return new ResponseEntity<>(project, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
