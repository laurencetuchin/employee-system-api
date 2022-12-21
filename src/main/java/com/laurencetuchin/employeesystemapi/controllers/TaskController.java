package com.laurencetuchin.employeesystemapi.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laurencetuchin.employeesystemapi.entities.*;
import com.laurencetuchin.employeesystemapi.exceptions.TaskNotFoundException;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.services.ProjectService;
import com.laurencetuchin.employeesystemapi.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task with name: " + name + " not found");
        }
        try {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(task, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get Task by Priority", description = "Get Task by Priority query", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "400", description = "Task not found",
                    content = @Content)})
    @GetMapping("/priority/")
    @Query("select t from Task t where t.priority = ?1")
    public ResponseEntity<List<Task>> findByPriority(@RequestParam TaskPriority priority) {
        List<Task> task = service.findByPriority(priority);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task with priority: " + priority + " not found");
        }
        try {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(task, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get Task by Date Ending", description = "Get Task by Date Ending query", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)})
    @GetMapping("/ending/") // update to sort by end date
    @Query("select t from Task t where t.endDate = ?1 order by t.endDate")
    public ResponseEntity<List<Task>> findByEndDateOrderByEndDateAsc(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<Task> endDateAsc = service.findByEndDateOrderByEndDateAsc(endDate);
        if (endDateAsc.isEmpty()) {
            throw new TaskNotFoundException("Task with end date: " + endDate + " not found");
        }
        try {
            return new ResponseEntity<>(endDateAsc, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(endDateAsc, HttpStatus.NOT_FOUND);
        } catch (MethodArgumentTypeMismatchException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Save Task", description = "Save Task, accepts RequestBody", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task Saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content)})
    @PostMapping("/save/")
    public ResponseEntity<Task> saveTask(@Valid @RequestBody Task task) {
        Task saveTask = service.saveTask(task);
        try {
            return new ResponseEntity<>(saveTask, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update Task", description = "Update Task by Id, accepts RequestBody, uses @PathVariable for id", tags = "Task", method = "Put",
            parameters = {@Parameter(name = "id", description = "Path Variable id for task", example = "1")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request Body description",
                    content = {@Content(
                            examples = {@ExampleObject(
                                    name = "endDate",
                                    value = "2033-12-17T04:02:44.491Z",
                                    description = "Date Task ends",
                                    summary = "End date must be in future time")}
                    )}
            ))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content)
    }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task, @PathVariable Long id) {
        Optional<Task> optionalTask = service.findTaskById(id);
        Task updateTask = service.updateTask(task, id);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException("Task with id: " + id + " not found");
        }
        try {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(task, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete Task", description = "Delete Task by id, accepts PathVariable", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task Deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content)})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteTaskById(@PathVariable Long id) {
        Optional<Task> task = service.findTaskById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task with id: " + id + " not found");
        }
        try {
            service.deleteTaskById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "Get all Tasks", description = "Get all Tasks", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Tasks Not Found",
                    content = @Content)})
    @GetMapping("/all")
    public ResponseEntity<List<Task>> findAll() {
        List<Task> taskList = service.findAll();
        if (taskList.isEmpty()) {
            throw new TaskNotFoundException("Tasks not found");
        }
        try {
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(taskList, HttpStatus.NOT_FOUND);
        }
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

    @Operation(summary = "Assign Task to Employee", description = "Assign Task to Employee, uses PathVariable for taskId and employeeId", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task assigned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task Not assigned",
                    content = @Content)})
    @PutMapping("/{taskId}/employee/{employeeId}")
    public ResponseEntity<Task> assignTaskToEmployee(@PathVariable Long taskId, @PathVariable Long employeeId) {
        Optional<Task> _task = service.findTaskById(taskId);
        Optional<Employee> _employee = employeeRepository.findById(employeeId);
        if (_task.isEmpty() || _employee.isEmpty()) {
            throw new TaskNotFoundException("Task with id: " + taskId + " or Employee with id: " + employeeId + " not found");
        }
        Task task = _task.get();
//            task.setEmployees(Collections.singleton(_employee.get()));
        task.addEmployee(_employee.get());
        service.saveTask(task);
        try {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(task, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Assign Task to Project", description = "Assign Task to Project, uses PathVariable for taskId and projectId", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task assigned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task Not assigned",
                    content = @Content)})
    @PutMapping("/{taskId}/project/{projectId}")
    public ResponseEntity<Task> assignTaskToProject(@PathVariable Long taskId, @PathVariable Long projectId) {
        Optional<Task> taskOptional = service.findTaskById(taskId);
        Optional<Project> projectOptional = projectService.findById(projectId);
        if (taskOptional.isEmpty() || projectOptional.isEmpty()) {
            throw new TaskNotFoundException("Task with id: " + taskId + " or project with id: " + projectId + " not found");
        }
        Task task = taskOptional.get();
        task.setProject(projectOptional.get());
        Task saveTask = service.saveTask(task);
        try {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(task, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Remove Task from Project", description = "Remove Task from Project, uses PathVariable for taskId and projectId", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task removed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not removed",
                    content = @Content)})
    @PutMapping("/project/{projectId}/task/{taskId}/remove")
    public ResponseEntity<Task> removeTaskFromProject(@PathVariable Long taskId, @PathVariable Long projectId) {
        Optional<Task> taskById = service.findTaskById(taskId);
        Optional<Project> projectbyId = projectService.findById(projectId);
        if (taskById.isEmpty() || projectbyId.isEmpty())    {
            throw new TaskNotFoundException("Task with id: %d or project with id: %d not found".formatted(taskId, projectId));
        }

        Task task = taskById.get();
        task.setProject(null);
        Task saveTask = service.saveTask(task);

        try {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(task, HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Find Task between dates", description = "Find Task ending between start and end date, includes exactly starting at and exactly ending at", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content)})
    @GetMapping("/find-between-dates")
    @Query("select t from Task t where t.startDate <= ?1 and t.endDate >= ?2 order by t.endDate")
    public List<Task> findBetweenStartAndEndDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startAsDateType, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endAsDateType) {
        LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(endAsDateType.toInstant(), ZoneId.systemDefault());
        LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(startAsDateType.toInstant(), ZoneId.systemDefault());

        return service.findByStartDateLessThanAndEndDateGreaterThan(startLocalDateTime, endLocalDateTime);
    }


    @Operation(summary = "Find Task ending in less than 7 days", description = "Find Task End Date ends 7 days", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content)})
    @GetMapping("/ends-week")
    @Query("select t from Task t where t.endDate >= ?1")
    public List<Task> findByEndDateGreaterThanEqual() {
        return service.findByEndDateLessThanEqual();
    }


    @Operation(summary = "Find Task with status not equal", description = "Find Task with status not equal e.g. not equal to complete will show non complete tasks", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content)})
    @Query("select t from Task t where t.status <> ?1 order by t.name")
    @GetMapping("/find-status-exclude")
    public List<Task> findByStatusNotOrderByNameAsc(@RequestParam TaskStatus status) {
        return service.findByStatusNotOrderByNameAsc(status);
    }


    @Operation(summary = "Find Task order end date", description = "Find Task order end date", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content)})
    @Query("select t from Task t order by t.endDate")
    @GetMapping("/end-asc")
    public List<Task> findByOrderByEndDateAsc() {
        return service.findByOrderByEndDateAsc();
    }

    @Operation(summary = "Find Task order end date not ended", description = "Find Task order end date not ended", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content)})
    @Query("select t from Task t where t.endDate >= ?1 order by t.endDate")
    @GetMapping("/not-ended")
    public List<Task> findByEndDateLessThanEqualOrderByEndDateAsc() {
        return service.findByEndDateLessThanEqualOrderByEndDateAsc();
    }
}
