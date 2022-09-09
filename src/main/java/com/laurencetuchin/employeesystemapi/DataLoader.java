package com.laurencetuchin.employeesystemapi;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner loadData(EmployeeRepository repository){
        return args -> {
            repository.save(new Employee("Frodo", "Ring bearer"));
            repository.save(new Employee("Bilbo", "Ring carer"));
            repository.save(new Employee("Samwise", "helper"));
        };
    }
}
