package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.ProjectStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.laurencetuchin.employeesystemapi.entities.ProjectStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee employee1 = new Employee("Tomas","Striker");

//    public Project project1 = new Project(1L,"Manchester United", PENDING, employee1);


    private List<Project> projectList = new ArrayList<>();


    @BeforeEach
    void setup(){
        // given
//        Project project1 = new Project("Manchester United","Fred", PENDING);
//        projectRepository.save(project1);
//        Project project2 = new Project(2L,"Chelsea", COMPLETE, new Employee("Jorginho","Midfielder"));
//        projectRepository.save(project2);
        // when
//        projectList.add(project1);
//        projectList.add(project2);


    }

    @Test
    void itShouldReturnProjectById() {
        // given
        // when
//        Project project1 = new Project("Manchester United","Fred", PENDING);
//        projectRepository.save(project1);
        Optional<Project> projectById = projectRepository.findProjectById(1L);
//        project1.setId(1L);
        // then
        assertThat(projectById.get().getId()).isEqualTo(1);
    }


    @Test
    void itShouldGiveProjectIdAfterSave(){
        // given
        Long id = projectRepository.findAll().get(0).getId();

        // when

        // then
        assertThat(id).isGreaterThan(0);

    }



    @Test
    void findByStatus() {
        // given
        List<Project> projectByStatus = projectRepository.findByStatus(progress);

        // when
        ProjectStatus status = projectByStatus.get(0).getStatus();

        // then
        assertThat(status).isEqualTo(progress);
    }


    @Test
    void findProjectByNameIgnoreCaseContains() {
        // given
        List<Project> project = projectRepository.findProjectByNameIgnoreCaseContains("man");
        // when


        // then
        assertThat(project.size()).isEqualTo(1);

    }

//    @Test
//    void findProjectByAssignedEmployeesIgnoreCaseContains() {
//        // given
//        List<Project> fred = projectRepository.findProjectByAssignedEmployeesIgnoreCaseContains("fred");
//
//
//        // then
//        assertThat(fred.get(0).getAssignedEmployees()).isEqualTo("Fred");
//    }


}