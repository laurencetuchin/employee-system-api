package com.laurencetuchin.employeesystemapi.seedData;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataLoader {

    @Autowired
    private EmployeeRepository repository;

    @Bean
    CommandLineRunner loadData(EmployeeRepository repository){
        return args -> {
            repository.save(new Employee("Frodo","Ring bearer"));
            repository.save(new Employee("Bilbo","Ring bearer"));
            repository.save(new Employee("samwise","ring friend"));

        };
    }
}
