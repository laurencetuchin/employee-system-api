package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.entities.TaskPriority;
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


    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public Task updateTask(Task task, Long id){
//        boolean taskExists = taskRepository.existsById(task.getId());
        Optional<Task> _task = taskRepository.findTaskById(id);
        if (!_task.isPresent()){
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

    public void deleteTaskById(Long id){
        taskRepository.deleteById(id);
    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }


    @Query("select t from Task t where t.startDate < ?1 and t.endDate > ?2")
    public List<Task> findByStartDateLessThanAndEndDateGreaterThan(LocalDateTime startDate, LocalDateTime endDate) {
        return taskRepository.findByStartDateLessThanAndEndDateGreaterThan(startDate, endDate);
    }

    @Query("select t from Task t where t.endDate < ?1 order by t.endDate")
    public List<Task> findByEndDateLessThanOrderByEndDateAsc(LocalDateTime endDate) {
        return taskRepository.findByEndDateLessThanOrderByEndDateAsc(endDate);
    }


}
