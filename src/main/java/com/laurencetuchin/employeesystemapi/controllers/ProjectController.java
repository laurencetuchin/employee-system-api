package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.ProjectStatus;
import com.laurencetuchin.employeesystemapi.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService service;


    @GetMapping("/find-by-name/{projectName}")
    public List<Project> findProjectByName(@RequestParam String projectName) {
        return service.findProjectByName(projectName);
    }

    @GetMapping("/find-by-status/{projectName}")
    public List<Project> findProjectByStatus(@RequestParam ProjectStatus projectName) {
        return service.findProjectByStatus(projectName);
    }
}
