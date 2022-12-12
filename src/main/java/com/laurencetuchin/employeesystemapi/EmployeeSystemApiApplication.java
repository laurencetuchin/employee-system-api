package com.laurencetuchin.employeesystemapi;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.Task;
import com.laurencetuchin.employeesystemapi.entities.TaskPriority;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.repositories.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SpringBootApplication(scanBasePackages = { "com.laurencetuchin.*" })

//@ComponentScan({"com.laurencetuchin.employeesystemapi.repositories", "com.laurencetuchin.employeesystemapi.seedData", "com.laurencetuchin.employeesystemapi.entities", "com.laurencetuchin.employeesystemapi.controllers", "com.laurencetuchin.employeesystemapi.services"})
//@ComponentScan("com.laurencetuchin.employeesystemapi.*")
@EnableAutoConfiguration
public class EmployeeSystemApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeSystemApiApplication.class, args);

	}

//	@Bean
//	public CommandLineRunner loadDatabase(EmployeeRepository employeeRepository){
//		return args -> {
//			employeeRepository.save(new Employee(""));
//			employeeRepository.save(new Employee(""));
//			employeeRepository.save(new Employee(""));
//		}
//	}




}
