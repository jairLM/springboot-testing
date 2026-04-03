package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

public class EmployeeServiceTest {


    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    //JUnit test for saveEmployee method
    @Test
    @DisplayName("JUnit test for saveEmployee method")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given - precondition
        Employee employee = Employee.builder()
                .id(1)
                .firstName("Luis Jair")
                .lastName("Lopez Murillo")
                .email("luizz.jair@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or behavior
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }


}
