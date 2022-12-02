package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    List<Project> findProjectByNameIgnoreCaseContains(String projectName);
    List<Project> findProjectByAssignedEmployeeIgnoreCaseContains(String assignedEmployee);
    List<Project> findProjectByStatusIgnoreCaseContains(String projectName);



}
