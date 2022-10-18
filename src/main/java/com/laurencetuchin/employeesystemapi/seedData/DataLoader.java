package com.laurencetuchin.employeesystemapi.seedData;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private EmployeeRepository repository;

    @Bean
    CommandLineRunner loadData(EmployeeRepository repository){
        return args -> {
            repository.save(new Employee("Frodo","Ring bearer"));
            log.info("Employee 1 saved in Database");
            repository.save(new Employee("Bilbo","Ring bearer"));
            log.info("Employee 2 saved in Database");
            repository.save(new Employee("samwise","ring friend"));
            log.info("preloading" + repository.save(new Employee("samwise","ring friend")));
            log.info(" 3 Employees saved in Database");

        };
    }
}
