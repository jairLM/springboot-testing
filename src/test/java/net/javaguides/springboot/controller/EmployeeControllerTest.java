package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("email@live.com")
                .build();

        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when - action or the behaviour that we are going to test
        ResultActions resultActions = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then - verify the output

        resultActions.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
        //given - precondition
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder().firstName("Jair").lastName("Murillo").email("luizz.jair@gmail.com").build());
        employeeList.add(Employee.builder().firstName("Evelyn").lastName("Serrano").email("evse@gmail.com").build());

        given(employeeService.getAllEmployees()).willReturn(employeeList);


        //when - action or behavior that we're going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));


        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(employeeList.size())));

    }

    //positive scenario
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        //given - precondition
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("email@live.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));


        //when - action or behavior that we're going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));


        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    //negative scenario
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        //given - precondition
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("email@live.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());


        //when - action or behavior that we're going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));


        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    //update Employee positive scenario
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() throws Exception {

        //given - precondition or set up
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("email@live.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Jair")
                .lastName("Lopez")
                .email("jair@live.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
        given(employeeService.saveEmployee(any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));

        //when - action or behavior that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));


    }


    //update Employee negative scenario
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {

        //given - precondition or set up
        long employeeId = 1L;

        Employee updatedEmployee = Employee.builder()
                .firstName("Jair")
                .lastName("Lopez")
                .email("jair@live.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        given(employeeService.saveEmployee(any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));

        //when - action or behavior that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());


    }




}
