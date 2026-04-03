package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Springboot-test provides @DataJpaTest annotation to test the persistence layer
 * components that will autoconfigure in memory embedded database for testing purposes.
 * Is not necessary to use mocks for this kind of tests.
 * @see DataJpaTest
 *
 * convention used in this test is (given_when_then*
 *
 */
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("JUnit test for saving employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        //given - precondition
        Employee employee = Employee.builder()
                .firstName("Luis Jair")
                .lastName("Lopez Murillo")
                .email("luizz.jair@gmail.com")
                .build();

        //when - action or behavior
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);


    }





}
