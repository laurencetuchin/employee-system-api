package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {


    @MockBean
    private EmployeeService employeeService; // Injects during runtime

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }


    @Test
    public void itShouldGetEmployeeByIdAndReturnEmployeeIfExists() {





    }

    @Test
    void getAllEmployees() {
        List<Employee> allEmployees = new ArrayList<>();
        allEmployees.add(new Employee("Frodo Baggins","Ring bearer",true));
        allEmployees.add(new Employee("Bilbo Baggins", "Ring thief",true));
        allEmployees.add(new Employee("Peter Potatohead", "Office admin",false));

        Mockito.when(employeeService.getAllEmployees()).thenReturn(allEmployees);
        String url = "/api/employees/all";
        mockMvc.perform(MockHttpServletRequest::new(url)).andExpect
    }

    @Test
    void getAllEmployeesDTO() {
    }

    @Test
    void getCurrentlyEmployedEmployees() {
    }

    @Test
    void findByNameIgnoreCaseContains() {
    }

    @Test
    void findByRoleIgnoreCaseContains() {
    }

    @Test
    void findEmployeeByNameAndRole() {
    }

    @Test
    void createEmployee() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() {
    }
}