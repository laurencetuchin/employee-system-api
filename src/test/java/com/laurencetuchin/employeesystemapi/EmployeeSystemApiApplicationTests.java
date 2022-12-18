package com.laurencetuchin.employeesystemapi;

import com.laurencetuchin.employeesystemapi.controllers.EmployeeController;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.EmploymentStatus;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeeSystemApiApplicationTests {

	@Autowired
	private EmployeeController controller;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;



	private Employee employee = new Employee("Tom", "potato maker", "tom@gmail.com", EmploymentStatus.employed, LocalDate.of(1111, 1, 1), "Grow old");

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
		assertThat(employeeService).isNotNull();
		assertThat(employeeRepository).isNotNull();
		assertThat(employee).isNotNull();
	}

}
