package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.ProjectStatus;
import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.exceptions.EmployeeNotFoundException;
import com.laurencetuchin.employeesystemapi.exceptions.ProjectNotFoundException;
import com.laurencetuchin.employeesystemapi.exceptions.TaskNotFoundException;
import com.laurencetuchin.employeesystemapi.repositories.ProjectRepository;
import com.laurencetuchin.employeesystemapi.repositories.TaskRepository;
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
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TaskRepository taskRepository;

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

    public Project updateProjectById( Project project, Long id){
        Optional<Project> _project = projectRepository.findProjectById(id);
        //noinspection OptionalGetWithoutIsPresent
        Project project1 = _project.get();
        if (_project.isEmpty()){
            throw new NoSuchElementException("Project with id: " + id + " does not exist");
        } else {
            project1.setName(project.getName());
//            _project.get().setEmployee(project.getEmployee());
            project1.setStatus(project.getStatus());
            project1.setStartDate(project.getStartDate());
            project1.setEndDate(project.getEndDate());
            project1.setTask(project.getTask());
            projectRepository.save(project1);
        }
        return project1;
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

//    @Transactional
//    public void addTaskToProject(Task task){
//
//    }


    @Query("select p from Project p where p.status <> ?1 order by p.name")
    public List<Project> findByStatusNotOrderByNameAsc(ProjectStatus status) {

        List<Project> projects = projectRepository.findByStatusNotOrderByNameAsc(status);
        if (projects.isEmpty()){
            throw new ProjectNotFoundException("No projects found excluding status: %s".formatted(status));
        }
        return projects;
    }

    @Query("select p from Project p where p.employee.id = ?1 order by p.name")
    public List<Project> findByEmployee_IdOrderByNameAsc(Long id) {
        // employee repo check if Employee exists
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        if (employee.isEmpty()){
            throw new EmployeeNotFoundException("Employee with id: %d not found".formatted(id));
        }

        // check for no projects under that employee
        List<Project> projects = projectRepository.findByEmployee_IdOrderByNameAsc(id);
        if (projects.isEmpty()){
            throw new ProjectNotFoundException("No Projects found with Employee id: %d".formatted(id));
        }
        return projects;
    }


    @Query("select p from Project p inner join p.task task where task.id = ?1")
    public List<Project> findByTask_Id(Long id) {

        Optional<Task> task = taskRepository.findTaskById(id);
        if (task.isEmpty()){
            throw new TaskNotFoundException("Task with id %d not found".formatted(id));
        }

        List<Project> projects = projectRepository.findByTask_Id(id);
        if (projects.isEmpty()){
            throw new ProjectNotFoundException("Project with task id: %d not found".formatted(id));
        }
        return projects;

    }
}
