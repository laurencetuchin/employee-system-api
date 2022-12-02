package com.laurencetuchin.employeesystemapi.repositories;

import com.laurencetuchin.employeesystemapi.entities.Project;
import com.laurencetuchin.employeesystemapi.entities.ProjectStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.laurencetuchin.employeesystemapi.entities.ProjectStatus.COMPLETE;
import static com.laurencetuchin.employeesystemapi.entities.ProjectStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    private Project project1 = new Project("Manchester United","Fred", PENDING);


    private List<Project> projectList = new ArrayList<>();

    @Test
    void itShouldLoadAppContext(){

    }

    @BeforeEach
    void setup(){
        // given
//        Project project1 = new Project("Manchester United","Fred", PENDING);
        projectRepository.save(project1);
        Project project2 = new Project("Chelsea", "Jorginho",COMPLETE);
        projectRepository.save(project2);
        // when
        projectList.add(project1);
        projectList.add(project2);


    }

    @Test
    void itShouldReturnProjectById() {
        // given
        // when
//        Project project1 = new Project("Manchester United","Fred", PENDING);
//        projectRepository.save(project1);
        Project projectById = projectRepository.findProjectById(1L);
//        project1.setId(1L);
        // then
        assertThat(projectById.getId()).isEqualTo(1);
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
    void countByAssignedEmployees() {
    }

    @Test
    void findByStatus() {
    }

    @Test
    void findProjectByNameIgnoreCaseContains() {
    }

    @Test
    void findProjectByAssignedEmployeeIgnoreCaseContains() {
    }

    @Test
    void findProjectByStatusIgnoreCaseContains() {
    }
}