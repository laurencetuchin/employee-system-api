package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.ProjectStatus;
import com.laurencetuchin.employeesystemapi.repositories.ProjectRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> findProjectByName(String projectName){
        return projectRepository.findProjectByNameIgnoreCaseContains(projectName);
    }

    public List<Project> findProjectByStatus(ProjectStatus status){
        return projectRepository.findByStatus(status);
    }

    public Project saveProject(Project project){
        return projectRepository.save(project);
    }

    public void deleteProject(Long id){
        boolean projectExists = projectRepository.existsById(id);
        if (!projectExists) {
            throw new IllegalStateException("Project with id: " + id + " doesn't exist");
        }
        projectRepository.deleteById(id);
    }

    public Project updateProjectById(@NotNull Project project){
        boolean employeeExists = projectRepository.existsById(project.getId());
        if (!employeeExists){
            throw new NoSuchElementException("Project with id" + project.getId() + "does not exist");
        } else {

            projectRepository.save(project);
        }
        return project;
    }


    public List<Project> findAll(){
        return projectRepository.findAll();
    }


    public Optional<Project> findById(Long id){
        return projectRepository.findById(id);
    }

}
