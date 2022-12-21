package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.ProjectStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query("select p from Project p where p.status = ?1")
    List<Project> findByStatus(ProjectStatus status);

    Optional<Project> findProjectById(Long id);

    List<Project> findProjectByNameIgnoreCaseContains(String projectName);

    @Query("select p from Project p where p.status = ?1 order by p.timeRemaining ASC")
    List<Project> findByStatusOrderByTimeRemainingDesc(ProjectStatus status);

    @Query("select p from Project p where upper(p.employee.name) = upper(?1)")
    List<Project> findByEmployee_NameAllIgnoreCase(String name);

    @Query("select p from Project p where p.status <> ?1 order by p.name")
    List<Project> findByStatusNotOrderByNameAsc(ProjectStatus status);

    @Query("select p from Project p where p.employee.id = ?1 order by p.name")
    List<Project> findByEmployee_IdOrderByNameAsc(Long id);



//    Optional<Project> findById(Long id);



    //    List<Project> findProjectByAssignedEmployeesIgnoreCaseContains(String assignedEmployee);



}
