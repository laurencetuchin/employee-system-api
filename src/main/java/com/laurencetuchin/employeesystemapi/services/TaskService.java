package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.entities.*;
import com.laurencetuchin.employeesystemapi.exceptions.EmployeeNotFoundException;
import com.laurencetuchin.employeesystemapi.exceptions.ProjectNotFoundException;
import com.laurencetuchin.employeesystemapi.exceptions.TaskNotFoundException;
import com.laurencetuchin.employeesystemapi.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeService employeeService;

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findTaskById(id);
    }

    public List<Task> findTaskByName(String name) {
        return taskRepository.findTaskByName(name);
    }

    @Query("select t from Task t where t.priority = ?1")
    public List<Task> findByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority);
    }

    @Query("select t from Task t where t.endDate = ?1 order by t.endDate")
    public List<Task> findByEndDateOrderByEndDateAsc(LocalDateTime endDate) {
        return taskRepository.findByEndDateOrderByEndDateAsc(endDate);
    }


    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Task task, Long id) {
//        boolean taskExists = taskRepository.existsById(task.getId());
        Optional<Task> _task = taskRepository.findTaskById(id);
        if (!_task.isPresent()) {
            throw new NoSuchElementException("Task with id: " + id + " does not exist");
        } else {
            _task.get().setName(task.getName());
            _task.get().setDescription(task.getDescription());
            _task.get().setStatus(task.getStatus());
            _task.get().setStartDate(task.getStartDate());
            _task.get().setEndDate(task.getEndDate());
            _task.get().setEmployees(task.getEmployees());
            _task.get().setPriority(task.getPriority());
            _task.get().setProject(task.getProject());
            taskRepository.save(_task.get());
        }
        return _task.get();
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }


    @Query("select t from Task t where t.startDate <= ?1 and t.endDate >= ?2 order by t.endDate")
    public List<Task> findByStartDateLessThanAndEndDateGreaterThan(LocalDateTime startDate, LocalDateTime endDate) {

        List<Task> tasks = taskRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByEndDateAsc(startDate, endDate);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Task with startDate: " + startDate + " or endDate: " + endDate + " not found");
        } else
            return tasks;
    }


    @Query("select t from Task t where t.endDate <= ?1")
    public List<Task> findByEndDateLessThanEqual() {
        LocalDateTime endsIn7Days = LocalDateTime.now().plusDays(7);

        List<Task> tasks = taskRepository.findByEndDateGreaterThanEqual(endsIn7Days);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks ending in 7 days found");
        }
        return tasks;
    }

    @Query("select t from Task t where t.status <> ?1 order by t.name")
    public List<Task> findByStatusNotOrderByNameAsc(TaskStatus status) {
        List<Task> tasks = taskRepository.findByStatusNotOrderByNameAsc(status);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found excluding status: %s".formatted(status));
        }
        return tasks;
    }


    @Query("select t from Task t order by t.endDate")
    public List<Task> findByOrderByEndDateAsc() {
        return taskRepository.findByOrderByEndDateAsc();
    }

    @Query("select t from Task t where t.endDate >= ?1 order by t.endDate")
    public List<Task> findByEndDateLessThanEqualOrderByEndDateAsc() {
        LocalDateTime endDate = LocalDateTime.now();
        return taskRepository.findByEndDateLessThanEqualOrderByEndDateAsc(endDate);
    }


    @Query("select t from Task t where t.project.id = ?1 order by t.name")
    public List<Task> findTaskByProjectId(Long id) {
        Optional<Project> project = projectService.findById(id);
        if (project.isEmpty()){
            throw new ProjectNotFoundException("Project with id: %d not found".formatted(id));
        }

        List<Task> tasks = taskRepository.findByProject_IdOrderByNameAsc(id);
        if (tasks.isEmpty()){
            throw new TaskNotFoundException("Tasks with project id: %d not found".formatted(id));
        }
        return tasks;
    }


    @Query("select t from Task t inner join t.employees employees where employees.id = ?1 order by t.name")
    public List<Task> findTasksByEmployeeId(Long id) {
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        if (employee.isEmpty()){
            throw new EmployeeNotFoundException("Employee with id %d not found".formatted(id));
        }
        List<Task> tasks = taskRepository.findByEmployees_IdOrderByNameAsc(id);
        if (tasks.isEmpty()){
            throw new TaskNotFoundException("Tasks with Employee id: %d not found".formatted(id));
        }
        return tasks;
    }



}
