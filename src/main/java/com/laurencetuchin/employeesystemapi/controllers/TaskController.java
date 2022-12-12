package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.entities.*;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.services.ProjectService;
import com.laurencetuchin.employeesystemapi.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task/")
public class TaskController {

    @Autowired
    private TaskService service;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeRepository employeeRepository;

    public TaskController(TaskService service) {
        this.service = service;
    }


    @GetMapping("/{id}")
    public Optional<Task> findTaskById(@PathVariable("id") Long id) {
        return service.findTaskById(id);
    }

    @GetMapping("/name/")
    public List<Task> findTaskByName(@RequestParam String name) {
        return service.findTaskByName(name);
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
    public Task saveTask(@RequestBody Task task) {
        return service.saveTask(task);
    }

    @PutMapping("/update/{id}")
    public Task updateTask(@RequestBody Task task,@PathVariable Long id) {
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
    public Task assignTaskToEmployee(@PathVariable Long taskId, @PathVariable Long employeeId){
        Optional<Task> _task = service.findTaskById(taskId);
        Optional<Employee> _employee = employeeRepository.findById(employeeId);
        _task.get().setEmployees(Collections.singleton(_employee.get()));
        service.saveTask(_task.get());
        return _task.get();
    }

    @PutMapping("/{taskId}/project/{projectId}")
    public Task assignTaskToProject(@PathVariable Long taskId, @PathVariable Long projectId){
        Optional<Task> taskOptional = service.findTaskById(taskId);
        Optional<Project> projectOptional = projectService.findById(projectId);
        taskOptional.get().setProject(projectOptional.get());
        service.saveTask(taskOptional.get()); // saving or updating
        return taskOptional.get();
    }



}
