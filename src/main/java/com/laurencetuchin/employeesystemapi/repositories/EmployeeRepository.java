package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.EmploymentStatus;
import com.laurencetuchin.employeesystemapi.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameIgnoreCaseContains(String partialName);

    List<Employee> findByRoleIgnoreCaseContains(String role);

//    List<Employee> findByIsCurrentlyWorkingAtCompany(boolean isCurrentlyWorkingAtCompany);


    List<Employee> findEmployeeByNameAndRole(String partialName, String role);

    List<Employee> findEmployeeByNameOrRoleAllIgnoreCase(String partialName, String role);

    @Query("select e from Employee e where upper(e.status) = upper(?1) order by e.name")
    List<Employee> findByEmploymentStatusAllIgnoreCaseOrderByNameAsc(EmploymentStatus status);

    @Query("select e from Employee e where e.status = ?1")
    List<Employee> findByStatus(EmploymentStatus status);

    @Query("select e from Employee e where e.tasks = ?1")
    List<Employee> findByTasks(Task tasks);

    @Query("select e from Employee e where e.id = ?1 and e.tasks = ?2")
    List<Employee> findByIdAndTasks(Long id, Task tasks);

    @Query("select e from Employee e inner join e.tasks tasks where tasks.id = ?1")
    List<Employee> findByTasks_Id(Long id);

    @Query("select e from Employee e inner join e.tasks tasks where e.id = ?1 and tasks.id = ?2")
    List<Employee> findByIdAndTasks_Id(Long e, Long id1);

    
    



    List<Employee> findByTasksAllIgnoreCase(Task tasks);

//    @Query("select e from Employee e where e.employeeTasks = ?1")
//    List<Employee> findByEmployeeTasks(Task employeeTasks);

//    @Query("select e from Employee e inner join e.employeeTasks employeeTasks where employeeTasks.name = :name")
//    List<Employee> findByEmployeeTasks_Name(@Param("name") String name);


//    List<Employee> findEmployeeByTaskId(Long taskId);






}

