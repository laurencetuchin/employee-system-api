package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.entities.TaskPriority;
import com.laurencetuchin.employeesystemapi.exceptions.TaskNotFoundException;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.services.ProjectService;
import com.laurencetuchin.employeesystemapi.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task")
@CrossOrigin(origins = "http://localhost:8081/*")
public class TaskController {

    @Autowired
    private final TaskService service;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeRepository employeeRepository;

    public TaskController(TaskService service) {
        this.service = service;
    }


    @Operation(summary = "Get Task by Id", description = "Get Task by Id", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "400", description = "Task not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> findTaskById(@PathVariable("id") Long id) {

        Optional<Task> task = service.findTaskById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task with id: " + id + " not found");
        }
        try {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(task, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get Task by Name", description = "Get Task by Name query", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "400", description = "Task not found",
                    content = @Content)})
    @GetMapping("/name/")
    public ResponseEntity<List<Task>> findTaskByName(@RequestParam String name) {

        List<Task> task = service.findTaskByName(name);
        if (task.isEmpty()){
            throw new TaskNotFoundException("Task with name: " + name + " not found");
        }
        try {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotFoundException e){
            return new ResponseEntity<>(task, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/priority/")
    @Query("select t from Task t where t.priority = ?1")
    public List<Task> findByPriority(@RequestParam TaskPriority priority) {
        return service.findByPriority(priority);
    }

    @GetMapping("/ending/") // update to sort by end date
    @Query("select t from Task t where t.endDate = ?1 order by t.endDate")
    public List<Task> findByEndDateOrderByEndDateAsc(LocalDateTime endDate) {
        return service.findByEndDateOrderByEndDateAsc(endDate);
    }


    @PostMapping("/save/") // request body????
    public Task saveTask(@Valid @RequestBody Task task) {
        return service.saveTask(task);
    }

    @PutMapping("/update/{id}")
    public Task updateTask(@Valid @RequestBody Task task, @PathVariable Long id) {
        return service.updateTask(task, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTaskById(@PathVariable Long id) {
        service.deleteTaskById(id);
    }

    @GetMapping("/all")
    public List<Task> findAll() {
        return service.findAll();
    }

//    @PutMapping("/{taskId}/tasks/{employeeId}")
//    Employee assignEmployeeToTask(
//            @PathVariable Long employeeId,
//            @PathVariable Long taskId
//    ){
//        Task task = service.findTaskById(taskId).get();
//
//    }

//    @PostMapping("/project/{projectId}/tasks")
//    public ResponseEntity<Task> createTask(@PathVariable("projectId") long projectId, @RequestBody Task taskRequest){
//        Task task = projectService.findById(projectId).map(project -> project.taskRequest.setProject(project));
//        return service.saveTask(taskRequest);
//    }

    @PutMapping("/{taskId}/employee/{employeeId}")
    public Task assignTaskToEmployee(@PathVariable Long taskId, @PathVariable Long employeeId) {
        Optional<Task> _task = service.findTaskById(taskId);
        Optional<Employee> _employee = employeeRepository.findById(employeeId);
        _task.get().setEmployees(Collections.singleton(_employee.get()));
        service.saveTask(_task.get());
        return _task.get();
    }

    @PutMapping("/{taskId}/project/{projectId}")
    public Task assignTaskToProject(@PathVariable Long taskId, @PathVariable Long projectId) {
        Optional<Task> taskOptional = service.findTaskById(taskId);
        Optional<Project> projectOptional = projectService.findById(projectId);
        taskOptional.get().setProject(projectOptional.get());
        service.saveTask(taskOptional.get()); // saving or updating
        return taskOptional.get();
    }

    @PutMapping("/project/{projectId}/task/{taskId}/remove")
    public Task removeTaskFromProject(@PathVariable Long taskId, @PathVariable Long projectId) {
        Optional<Task> taskById = service.findTaskById(taskId);
        Optional<Project> projectbyId = projectService.findById(projectId);
        taskById.get().setProject(null);

        service.saveTask(taskById.get());
        return taskById.get();
    }


}
