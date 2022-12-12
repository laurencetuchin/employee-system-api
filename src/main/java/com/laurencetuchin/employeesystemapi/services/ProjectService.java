package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.ProjectStatus;
import com.laurencetuchin.employeesystemapi.repositories.ProjectRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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

    public Project updateProjectById(@NotNull Project project, Long id){
        Optional<Project> _project = projectRepository.findProjectById(id);
        if (!_project.isPresent()){
            throw new NoSuchElementException("Project with id: " + id + " does not exist");
        } else {
            _project.get().setName(project.getName());
            _project.get().setEmployee(project.getEmployee());
            _project.get().setStatus(project.getStatus());
            _project.get().setStartDate(project.getStartDate());
            _project.get().setEndDate(project.getEndDate());
            _project.get().setTask(project.getTask());
            projectRepository.save(_project.get());
        }
        return _project.get();
    }


    public List<Project> findAll(){
        return projectRepository.findAll();
    }


    public Optional<Project> findById(Long id){
        return projectRepository.findById(id);
    }

    @Query("select p from Project p where upper(p.employee.name) = upper(?1)")
    public List<Project> findByEmployee_NameAllIgnoreCase(String name) {
        return projectRepository.findByEmployee_NameAllIgnoreCase(name);
    }
}
