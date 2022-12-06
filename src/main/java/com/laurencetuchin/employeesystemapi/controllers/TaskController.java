package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.entities.TaskPriority;
import com.laurencetuchin.employeesystemapi.entities.TaskStatus;
import com.laurencetuchin.employeesystemapi.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task/")
public class TaskController {

    @Autowired
    private TaskService service;


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


    @PostMapping("/save/{id}")
    public Task saveTask(@PathVariable Task task) {
        return service.saveTask(task);
    }

    @PutMapping("/update/{id}")
    public Task updateTask(@RequestBody Task task,@PathVariable Long id) {
        return service.updateTask(task);
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


}
