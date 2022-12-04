package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.entities.TaskPriority;
import com.laurencetuchin.employeesystemapi.entities.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    Optional<Task> findTaskById(Long id);
    List<Task> findTaskByName(String name);


    @Query("select t from Task t where t.priority = ?1")
    List<Task> findByPriority(TaskPriority priority);

    @Query("select t from Task t where t.endDate = ?1 order by t.endDate")
    List<Task> findByEndDateOrderByEndDateAsc(LocalDateTime endDate);

    @Transactional
    @Modifying
    @Query("delete from Task t where t.status = ?1")
    int deleteByStatus(TaskStatus status);


}
