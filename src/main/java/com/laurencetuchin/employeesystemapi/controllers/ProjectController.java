package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.ProjectStatus;
import com.laurencetuchin.employeesystemapi.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService service;

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

    @GetMapping("/id/{id}")
    public Optional<Project> findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }
}
