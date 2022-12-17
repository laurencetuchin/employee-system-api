package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameIgnoreCaseContains(String partialName);

    List<Employee> findByRoleIgnoreCaseContains(String role);

//    List<Employee> findByIsCurrentlyWorkingAtCompany(boolean isCurrentlyWorkingAtCompany);


    List<Employee> findEmployeeByNameAndRole(String partialName, String role);

    List<Employee> findEmployeeByNameOrRoleAllIgnoreCase(String partialName, String role);




    List<Employee> findByTasksAllIgnoreCase(Task tasks);

//    @Query("select e from Employee e where e.employeeTasks = ?1")
//    List<Employee> findByEmployeeTasks(Task employeeTasks);

//    @Query("select e from Employee e inner join e.employeeTasks employeeTasks where employeeTasks.name = :name")
//    List<Employee> findByEmployeeTasks_Name(@Param("name") String name);


//    List<Employee> findEmployeeByTaskId(Long taskId);






}

