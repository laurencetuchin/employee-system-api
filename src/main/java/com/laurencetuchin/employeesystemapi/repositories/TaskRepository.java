package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.entities.TaskPriority;
import com.laurencetuchin.employeesystemapi.entities.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Transactional
    void deleteByProjectId(long projectId);

    @Query("select t from Task t where t.priority = ?1")
    List<Task> findByPriority(TaskPriority priority);

    @Query("select t from Task t where t.endDate = ?1 order by t.endDate")
    List<Task> findByEndDateOrderByEndDateAsc(LocalDateTime endDate);

    @Query("select t from Task t where t.startDate <= ?1 and t.endDate >= ?2 order by t.endDate")
    List<Task> findByStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByEndDateAsc(LocalDateTime startDate, LocalDateTime endDate);

    @Query("select t from Task t where t.endDate <= ?1")
    List<Task> findByEndDateGreaterThanEqual(LocalDateTime endDate);

    @Query("select t from Task t where t.status <> ?1 order by t.name")
    List<Task> findByStatusNotOrderByNameAsc(TaskStatus status);

    @Query("select t from Task t order by t.endDate")
    List<Task> findByOrderByEndDateAsc();

    @Query("select t from Task t where t.endDate >= ?1 order by t.endDate")
    List<Task> findByEndDateLessThanEqualOrderByEndDateAsc(LocalDateTime endDate);





//    List<Task> findTaskByEmployeeId(Long employeeId);




}
