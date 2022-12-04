package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.entities.TaskPriority;
import com.laurencetuchin.employeesystemapi.entities.TaskStatus;
import com.laurencetuchin.employeesystemapi.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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

    @DeleteMapping("/delete/{status}")
    @Transactional
    @Modifying
    @Query("delete from Task t where t.status = ?1")
    public int deleteByStatus(@RequestParam TaskStatus status) {
        return service.deleteByStatus(status);
    }

    @PostMapping("/save/{id}")
    public Task saveTask(@PathVariable Task task) {
        return service.saveTask(task);
    }

    @PutMapping("/update/{id}")
    public Task updateTask(@PathVariable Task task) {
        return service.updateTask(task);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTaskById(@PathVariable Long id) {
        service.deleteTaskById(id);
    }


}
