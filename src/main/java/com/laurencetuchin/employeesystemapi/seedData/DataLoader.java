package com.laurencetuchin.employeesystemapi.seedData;

import com.laurencetuchin.employeesystemapi.entities.*;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.repositories.ProjectRepository;
import com.laurencetuchin.employeesystemapi.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;


@Configuration
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

//    @Autowired
//    private EmployeeRepository repository;

    @Bean
    CommandLineRunner loadData(EmployeeRepository repository, ProjectRepository projectRepository, TaskRepository taskRepository){
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

             log.info("Total projects: " + projectRepository.save(new Project(1L,"Manchester", ProjectStatus.PENDING,employee)));
             Project project = projectRepository.findAll().get(0);
             log.info("Total tasks: " + taskRepository.save(new Task("Play game", "Play game very well", TaskStatus.progress,TaskPriority.high, LocalDateTime.now(), LocalDateTime.now().plusDays(1),project)));

//            log.info("Project info: " + projectRepository.findAll().get(0).toString());
        };
    }
}
