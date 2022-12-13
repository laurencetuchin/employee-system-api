package com.laurencetuchin.employeesystemapi.seedData;

import com.laurencetuchin.employeesystemapi.entities.*;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.repositories.ProjectRepository;
import com.laurencetuchin.employeesystemapi.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.*;


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

             log.info("Total projects: " + projectRepository.save(new Project(1L,"Manchester", ProjectStatus.pending)));
             log.info("Total projects: " + projectRepository.save(new Project(2L,"Chelsea", ProjectStatus.pending)));
//             Project project = projectRepository.findAll().get(0);
//             Project project2 = projectRepository.findAll().get(1);
             log.info("Total tasks: " + taskRepository.save(new Task("Play game","Play game very well",TaskStatus.progress,TaskPriority.high,LocalDateTime.now(),LocalDateTime.now().plusDays(1))));
             log.info("Total tasks: " + taskRepository.save(new Task("game","Play game well",TaskStatus.progress,TaskPriority.high,LocalDateTime.now(),LocalDateTime.now().plusDays(1))));
//            log.info("Returned task: " +  taskRepository.findAll().get(0));
            log.info("task employee assigned: ");
//            Set<Employee> assignedEmployees = new HashSet<>();
//            assignedEmployees.add(employee);
//            Task task = taskRepository.findAll().get(0);
//            Optional<Employee> employee1 = repository.findById(1L);
//            Employee employee2 = employee1.get();
//
//            Optional<Task> task = taskRepository.findTaskById(1L);
//            Task task1 = task.get();

//            task1.addEmployee(employee2);

//                    task.setProject(project2);
//            List<Task> taskList = new ArrayList<>();
//            taskList.add(task);
//            project2.setName("super");
//            project2.setTask(taskList);
//                    log.info("project details: " + project2);


//            task.setEmployeesAssignedTask(assignedEmployees);
//            Set<Task> assignedTasks = new HashSet<>();
//            assignedTasks.add(task);
//            employee.setEmployeeTasks(assignedTasks);
//            log.info("Project info: " + projectRepository.findAll().get(0).toString());
        };
    }
}
