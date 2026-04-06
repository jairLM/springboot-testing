package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    private List<Employee> employeeList;

    @BeforeEach
    public void setUp() {
        //employeeRepository = mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1)
                .firstName("Luis Jair")
                .lastName("Lopez Murillo")
                .email("luizz.jair@gmail.com")
                .build();

        employeeList = List.of(
                employee,
                Employee.builder()
                        .id(2)
                        .firstName("Evelyn")
                        .lastName("Serrano Rocha")
                        .email("evse@gmail.com")
                        .build()

        );
    }

    //JUnit test for saveEmployee method
    @Test
    @DisplayName("JUnit test for saveEmployee method")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given - precondition
        //setUp()

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or behavior
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }


    //JUnit test for saveEmployee method
    @Test
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        //given - precondition
        //setUp()

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        //when
        assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));

        //then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));

    }

    //JUnit test for testing get all employees
    @Test
    @DisplayName("JUnit test for testing get all employees")
    public void givenEmployeeList_whenGetAllEmployees_thenReturnAllEmployeesList() {
        //given - precondition
        given(employeeRepository.findAll()).willReturn(employeeList);

        //when - action or behavior
        List<Employee> employeeListInDb = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeListInDb).isNotNull();
        assertThat(employeeListInDb.size()).isEqualTo(2);

    }

    //JUnit test for testing get all employees
    @Test
    @DisplayName("JUnit test for testing get all employees (negative scenario)")
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        //given - precondition
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or behavior
        List<Employee> employeeListInDb = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeListInDb).isEmpty();

    }

    //JUnit test for get an employee by id
    @Test
    @DisplayName("JUnit test for get an employee by id")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        //given - precondition
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when - action or behavior
        Employee employeeDb = employeeService.getEmployeeById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDb).isNotNull();

    }

    //JUnit test for update employee method
    @Test
    @DisplayName("JUnit test for update employee method")
    public void givenAnEmployeeObject_whenUpdateEmployee_thenReturnUpdateEmployee(){
        //given - precondition
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("brook_98@live.com");

        //when - action or behavior
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("brook_98@live.com");

    }


}
