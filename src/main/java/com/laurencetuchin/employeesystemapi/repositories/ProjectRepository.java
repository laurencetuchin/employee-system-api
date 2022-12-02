package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query("select count(p) from Project p where p.assignedEmployees = ?1")
    long countByAssignedEmployees(String assignedEmployees);
    @Query("select p from Project p where p.status = ?1")
    List<Project> findByStatus(Project.ProjectStatus status);

    List<Project> findProjectByNameIgnoreCaseContains(String projectName);
    List<Project> findProjectByAssignedEmployeeIgnoreCaseContains(String assignedEmployee);
    List<Project> findProjectByStatusIgnoreCaseContains(String projectName);



}
