package com.laurencetuchin.employeesystemapi.services;

import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.entities.TaskPriority;
import com.laurencetuchin.employeesystemapi.entities.TaskStatus;
import com.laurencetuchin.employeesystemapi.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

    @Query("delete from Task t where t.status = ?1")
    @Modifying
    @Transactional
    public int deleteByStatus(TaskStatus status) {
        return taskRepository.deleteByStatus(status);
    }

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public Task updateTask(Task task){
        return taskRepository.save(task);
    }

    public void deleteTaskById(Long id){
        taskRepository.deleteById(id);
    }


}
