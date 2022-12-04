package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task/")
public class TaskController {

    @Autowired
    private TaskService service;


    public TaskController(TaskService service) {
        this.service = service;
    }



}
