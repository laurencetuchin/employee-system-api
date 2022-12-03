package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.ProjectStatus;
import com.laurencetuchin.employeesystemapi.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> findProjectByName(String projectName){
        return projectRepository.findProjectByNameIgnoreCaseContains(projectName);
    }

    public List<Project> findProjectByStatus(ProjectStatus projectName){
        return projectRepository.findByStatus(projectName);
    }

//    public List<Project> findProjectByAssignedEmployee(String employee){
//        return projectRepository.findProjectByAssignedEmployeesIgnoreCaseContains(employee);
//    }

    public List<Project> findAll(){
        return projectRepository.findAll();
    }


    public Optional<Project> findById(Long id){
        return projectRepository.findById(id);
    }

}
