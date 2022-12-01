package com.laurencetuchin.employeesystemapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laurencetuchin.employeesystemapi.entities.Employee;
import com.laurencetuchin.employeesystemapi.repositories.EmployeeRepository;
import com.laurencetuchin.employeesystemapi.services.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.print.attribute.standard.Media;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService; // Injects during runtime


    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;


    Employee employee1 = new Employee("Bruno Fernandes","Midfielder",true);

    Employee employee2 = new Employee("Diogo Dalot","Right Back",true);
    Employee employee3 = new Employee("Joao Felix","Second Striker",true);



    @BeforeEach
    void setup() {
        Employee employee = new Employee("Cristiano Ronaldo","Striker", true);
//        EmployeeService service = new EmployeeService(employeeRepository);
//        service.save(employee);

        // setup Mock
//        employeeServiceMock = mock(EmployeeService.class);
        employeeRepository.save(employee1);
        System.out.println("Cookie master");
        System.out.println(employeeRepository.findAll());
        employee1.setId(1L);
        employee2.setId(2L);
        employee3.setId(3L);
        System.out.println(employee1.getId());
        System.out.println(employee2.getId());
        System.out.println(employee3.getId());


    }


    @Test
    void getAllEmployees() throws Exception {
        List<Employee> employees = new ArrayList<>(Arrays.asList(employee1, employee2, employee3));
        // stub
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(
                get("/api/employees/all")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", Matchers.is("Joao Felix")))
                .andExpect(jsonPath("$[2].role", Matchers.is("Second Striker")))
                .andExpect(jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(jsonPath("$[2].currentlyWorkingAtCompany", Matchers.is(true)))

//                .andExpect((ResultMatcher) jsonPath("$[2].name", is("Joao Felix"))
                ;

    }

    @Test
    void itShouldReturnEmployeeWithId() throws Exception {
        // given
        List<Employee> employeeList = new ArrayList<>(Arrays.asList(employee1,employee2,employee3));
        // when
        Long id = 1L;
        when(employeeService.findEmployeeById(id)).thenReturn(Optional.of(employee1));
        // then
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", id)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(employee1.getName()))
                .andExpect(jsonPath("$.role").value(employee1.getRole()))
                .andDo(print());
//                .contentType(MediaType.APPLICATION_JSON)
//                .param("id","1")



//
//        response.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$",hasSize(1)))
////                .andExpect(
//                .andExpect(jsonPath("$.name",CoreMatchers.is("Bruno Fernandes")) )
//        ;

    }
    @Test
    void itShouldReturnEmployeeWithIdRequest() throws Exception {
        // given
        List<Employee> employeeList = new ArrayList<>(Arrays.asList(employee1,employee2,employee3));
        // when
//        when(employeeService.findEmployeeById(1L)).thenReturn(Optional.ofNullable(employee1).map(employee -> Collections.singletonList(employee).get(0)));
        when(employeeService.findEmployeeById(1L)).thenReturn(Optional.ofNullable(employee1));
//        String id = "1";
        // then
        ResultActions response = mockMvc.perform(get("/api/employees/request/1")
                .contentType(MediaType.APPLICATION_JSON)
//                .param("id", String.valueOf(1L))


        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$",hasSize(1)))
//                .andExpect()
//                .andExpect(
//                .andExpect(jsonPath("$[0].name",CoreMatchers.is("Bruno Fernandes")) )
        ;

    }

    @Test
    void itShouldReturnListOfEmployees() throws Exception {
        List<Employee> employeeList = new ArrayList<>(Arrays.asList(employee1,employee2,employee3));

        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        mockMvc.perform(get("/api/employees/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(employeeList.size()))
                .andDo(print());
    }

    @Test
    void itShouldCreateEmployee() throws Exception {
        Employee employee = new Employee("Bukayo Saka","Right Winger",true);

        mockMvc.perform(post("/api/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andDo(print());


    }


    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }


    @Test
    public void itShouldGetEmployeeByIdAndReturnEmployeeIfExists() {



    }


    @Test
    void itShouldReturnListOfEmployeesWithEmploymentStatusBooleanTrue() throws Exception{
        List<Employee> employeeList = new ArrayList<>(Arrays.asList(employee1,employee2,employee3));

        boolean result = true;
        when(employeeService.findCurrentlyEmployedEmployees(result)).thenReturn(employeeList);

        mockMvc.perform(get("/api/employment")
                .contentType(MediaType.APPLICATION_JSON)
                .param("result", String.valueOf(true))

        )           .andExpect(jsonPath("$.size()").value(employeeList.size()))
                .andExpect(status().isFound())
                .andDo(print());

    }

    @Test
    void givenNoBooleanArguments_whenGetSpecificException_thenMissingServletRequestParameterException() throws Exception {
        String exceptionParam = "bad_request";

        mockMvc.perform(get("/api/employment{result}", exceptionParam)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException))
                .andExpect(result -> assertEquals("Required request parameter 'result' for method parameter type boolean is not present", result.getResolvedException().getMessage())
        );

//        doThrow(Exception.class).when(employeeService).findCurrentlyEmployedEmployees(Boolean.parseBoolean(result));
//        employeeService.findCurrentlyEmployedEmployees(true);



    }

//    @Test
//    void shouldGetAllEmployees() throws Exception {
//        mockMvc.perform(get("/api/employees/all")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockRestRequestMatchers.content()
//                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].name", is("bob")));
//    }

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
        when(employeeService.findByRoleIgnoreCaseContains("ring"))
                .thenReturn(List.of());

        try {
            mockMvc.perform(get("/api/search/role{role}"))
                    .andDo(print())
                    .andExpect(content().string(equalTo("nothing")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verify(employeeService.findByRoleIgnoreCaseContains("ring"));
    }


    @Test
    void findEmployeeByNameAndRole() {
    }


    @Test
    void EmployeeController_CreateEmployee_ReturnCreated() throws Exception {
        given(employeeService.addNewEmployee(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("")));

        response.andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void EmployeeController_GetAllEmployee_ReturnResponse() throws Exception {
//        Employee employee = new Employee("Cristiano Ronaldo","Striker",true);
        List<Employee> employeeList = new ArrayList<>((Arrays.asList(employee1, employee2, employee3)));
//        employeeService.save(employee);
//        List<Employee> allEmployees = employeeService.getAllEmployees();
        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        ResultActions response = mockMvc.perform(get("/api/employees/all")
                        .contentType(MediaType.APPLICATION_JSON)

//                .param("pageNumber","1")
//                        .param("role","ring")



        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", CoreMatchers.is(employee3.getName()))
                );

    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() throws Exception {
         doNothing().when(employeeService).deleteEmployeeById(1L);

         ResultActions response = mockMvc.perform(delete("/api/employee/delete/1")
                 .contentType(MediaType.APPLICATION_JSON));

         response.andExpect(MockMvcResultMatchers.status().isOk());

    }


    @Test
    public void itShouldSearchUserByName() throws Exception {

        List<Employee> employeeList = new ArrayList<>(Arrays.asList(employee1,employee2,employee3));
        String partialName = "joao";

        // stub
        when(employeeService.findByNameIgnoreCaseContains(partialName)).thenReturn(Collections.singletonList(employee3));

        ResultActions response = mockMvc.perform(get("/api/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("partialName","joao")
//                .param("badParam","badvalue")
        );

        response.andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is(employee3.getName())))
                .andExpect(jsonPath("$[0].role", CoreMatchers.is(employee3.getRole()))
        );

    }
}