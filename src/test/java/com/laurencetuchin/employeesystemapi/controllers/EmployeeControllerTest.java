package com.laurencetuchin.employeesystemapi.controllers;

import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

//    @Test
//    void getAllEmployees() throws Exception {
//        List<Employee> allEmployees = new ArrayList<>();
//        allEmployees.add(new Employee("Frodo Baggins","Ring bearer",true));
//        allEmployees.add(new Employee("Bilbo Baggins", "Ring thief",true));
//        allEmployees.add(new Employee("Peter Potatohead", "Office admin",false));
//
//        Mockito.when(employeeService.getAllEmployees()).thenReturn(allEmployees);
//        String url = "/api/employees/all";
//        mockMvc.perform(MockMvcRequestBuilders
//                .get(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content()
//
//        );
//
//    }

    @Test
    void getAllEmployees2() throws Exception {
        given(employeeService.getAllEmployees())
                .willReturn(Arrays.asList(new Employee(
                        "Freddy Peters",
                        "Nothing",
                        false)));
        mockMvc.perform(get("/api/employees/all"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].name").value("Freddy Peters"));
        verify(employeeService.getAllEmployees());
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