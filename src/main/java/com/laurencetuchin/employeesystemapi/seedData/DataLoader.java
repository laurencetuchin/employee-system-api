package com.laurencetuchin.employeesystemapi.seedData;

import com.laurencetuchin.employeesystemapi.entities.*;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.repositories.ProjectRepository;
import com.laurencetuchin.employeesystemapi.repositories.TaskRepository;
import com.laurencetuchin.employeesystemapi.services.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;


@Configuration
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

//    @Autowired
//    private EmployeeRepository repository;
//
//    private static List<Employee> createEmployeeData(EmployeeRepository employeeRepository, ProjectRepository projectRepository, TaskRepository taskRepository){
//        return em
//    }
////
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    @Autowired
//    private TaskRepository taskRepository;

    @Bean
    CommandLineRunner loadData(EmployeeRepository employeeRepository, ProjectRepository projectRepository, TaskRepository taskRepository) {
        return args -> {
            employeeRepository.save(new Employee("Frodo", "Ring bearer", "frodo@gmail.com", EmploymentStatus.employed, LocalDate.now(), "Destroy the ring"));
            Employee employee = employeeRepository.findAll().get(0);
            log.info("Employee 1 saved in Database");
            employeeRepository.save(new Employee("Bilbo","Ring bearer","bilboswaggins@gmail.com",EmploymentStatus.employed,LocalDate.of(1811,1,1),"Steal frodos ring"));
            employeeRepository.save(new Employee("Bilbo2","Delivery", "bilbothedriver@gmail.com",EmploymentStatus.unemployed, LocalDate.of(1951,2,15), "Help frodos"));
            log.info("Employee 2 saved in Database");
            employeeRepository.save(new Employee("samwise","ring friend","samunwise@gmail.com",EmploymentStatus.redunandant, LocalDate.of(1979,9,15),"Help mr frodo" ));
            log.info("preloading" + employeeRepository.save(new Employee("Mr Log","information provider","tothelogman@gmail.com",EmploymentStatus.onleave,LocalDate.of(2000,5,5),"Provide information")));
            employeeRepository.save(new Employee("John Smith","Hardest worker", "johnsmith@gmail.com",EmploymentStatus.employed,LocalDate.of(1961,7,7),"Find secrets of the island"));
            log.info("Total employees: " + employeeRepository.count());

            log.info("Total projects: " + projectRepository.save(new Project( "Manchester", ProjectStatus.progress,LocalDateTime.now().plusDays(1),LocalDateTime.now().plusDays(5))));
            log.info("Total projects: " + projectRepository.save(new Project( "Chelsea", ProjectStatus.progress,LocalDateTime.now().plusDays(1),LocalDateTime.now().plusDays(3))));
            log.info("Total projects: " + projectRepository.save(new Project( "Arsenal", ProjectStatus.review,LocalDateTime.now().plusDays(10),LocalDateTime.now().plusDays(50))));
            log.info("Total projects: " + projectRepository.save(new Project( "Spurs", ProjectStatus.complete,LocalDateTime.now().plusDays(100),LocalDateTime.now().plusDays(500))));
             Project project = projectRepository.findAll().get(0);
//             Project project2 = projectRepository.findAll().get(1);
            log.info("Total tasks: " + taskRepository.save(new Task("Play game", "Play game very well", TaskStatus.progress, TaskPriority.high, LocalDateTime.now(), LocalDateTime.now().plusDays(1))));
            log.info("Total tasks: " + taskRepository.save(new Task("game", "Play game well", TaskStatus.progress, TaskPriority.high, LocalDateTime.now(), LocalDateTime.now().plusDays(1))));
            log.info("Total tasks: " + taskRepository.save(new Task("game3", "Play game well", TaskStatus.progress, TaskPriority.high, LocalDateTime.now(), LocalDateTime.now().plusDays(7))));
            log.info("Total tasks: " + taskRepository.save(new Task("game4", "Play game well", TaskStatus.progress, TaskPriority.high, LocalDateTime.now(), LocalDateTime.now().plusDays(8))));
            log.info("Total tasks: " + taskRepository.save(new Task("game5", "Play game well", TaskStatus.review, TaskPriority.high, LocalDateTime.now().minusDays(7), LocalDateTime.now().minusDays(2))));
            log.info("Total tasks: " + taskRepository.save(new Task("game6", "Play game well", TaskStatus.todo, TaskPriority.low, LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(2))));
//            log.info("Returned task: " +  taskRepository.findAll().get(0));
            log.info("task employee assigned: ");
//            Set<Employee> assignedEmployees = new HashSet<>();
//            assignedEmployees.add(employee);
//            Task task = taskRepository.findAll().get(0);
            Task task1 = new Task("game7", "Play game well", TaskStatus.todo, TaskPriority.low, LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(2));
//            project.addTask(task1);
//            projectRepository.save(project);
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
