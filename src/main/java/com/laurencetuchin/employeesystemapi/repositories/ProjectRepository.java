package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
}
