package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, CrudRepository<Employee, Long> {

}
