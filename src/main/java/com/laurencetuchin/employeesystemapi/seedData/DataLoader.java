package com.laurencetuchin.employeesystemapi.seedData;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.ProjectStatus;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.repositories.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

//    @Autowired
//    private EmployeeRepository repository;

    @Bean
    CommandLineRunner loadData(EmployeeRepository repository, ProjectRepository projectRepository){
        return args -> {
            repository.save(new Employee("Frodo","Ring bearer"));
            Employee employee = repository.findAll().get(0);
            log.info("Employee 1 saved in Database");
            repository.save(new Employee("Bilbo","Ring bearer"));
            repository.save(new Employee("Bilbo2","Delivery"));
            log.info("Employee 2 saved in Database");
            repository.save(new Employee("samwise","ring friend"));
            log.info("preloading" + repository.save(new Employee("Mr Log","information provider")));
            repository.save(new Employee("John Smith","Hardest worker", true));
            log.info("Total employees: " + repository.count());

             log.info("Total projects: " + projectRepository.save(new Project(1L,"Manchester United","frodo", ProjectStatus.PENDING,employee)));

//            log.info("Project info: " + projectRepository.findAll().get(0).toString());
        };
    }
}
