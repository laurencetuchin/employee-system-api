package com.laurencetuchin.employeesystemapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class EmployeeSystemApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeSystemApiApplication.class, args);
	}

}
