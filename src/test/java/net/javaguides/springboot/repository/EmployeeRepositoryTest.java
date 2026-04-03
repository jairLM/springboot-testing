package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Springboot-test provides @DataJpaTest annotation to test the persistence layer
 * components that will autoconfigure in memory embedded database for testing purposes.
 * Is not necessary to use mocks for this kind of tests.
 *
 * @see DataJpaTest
 * <p>
 * convention used in this test is (given_when_then*
 *
 */
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("JUnit test for saving employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

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

    //JUnit test for get all employees operation
    @Test
    @DisplayName("JUnit test for get all employees operation")
    public void givenEmployeesList_whenFindAll_thenReturnEmployeesList() {
        //given - precondition
        Employee employee1 = Employee.builder()
                .firstName("Luis Jair")
                .lastName("Lopez Murillo")
                .email("luizz.jair@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Evelyn")
                .lastName("Serrano Rocha")
                .email("evse@gmail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        //when - action or behavior
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    //JUnit test for get employee by id
    @Test
    @DisplayName("JUnit test for get employee by id")
    public void givenAEmployee_whenFindById_thenReturnAEmployee() {
        //given - precondition
        Employee employee = Employee.builder()
                .firstName("Luis Jair")
                .lastName("Lopez Murillo")
                .email("luizz.jair@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or behavior
        Employee employeeDb = employeeRepository.findById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDb).isNotNull();
    }

    //JUnit test to get an employee by email using custom method in repository
    @Test
    @DisplayName("JUnit test to get an employee by email using custom method in repository")
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployee() {
        //given - precondition
        Employee employee = Employee.builder()
                .firstName("Luis Jair")
                .lastName("Lopez Murillo")
                .email("luizz.jair@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or behavior
        Employee employeeDb = employeeRepository.findByEmail(employee.getEmail()).get();

        //then - verify the output
        assertThat(employeeDb).isNotNull();
    }

    //JUnit test to update an employee
    @Test
    @DisplayName("JUnit test to update an employee")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given - precondition
        Employee employee = Employee.builder()
                .firstName("Luis Jair")
                .lastName("Lopez Murillo")
                .email("luizz.jair@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or behavior
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("brook_98@live.com");
        savedEmployee.setFirstName("jairsz");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("brook_98@live.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("jairsz");

    }

    //JUnit test to delete employee operation
    @Test
    @DisplayName("JUnit test to delete employee operation")
    public void givenAEmployee_whenDelete_thenRemoveEmployee() {
        //given - precondition
        Employee employee = Employee.builder()
                .firstName("Luis Jair")
                .lastName("Lopez Murillo")
                .email("luizz.jair@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or behavior
        employeeRepository.delete(employee);
        Optional<Employee> employeeDeleted = employeeRepository.findById(employee.getId());

        //then - verify the output
        assertThat(employeeDeleted).isEmpty();

    }

    //JUnit test for custom query using JPQL with index params
    @Test
    @DisplayName("JUnit test for custom query using JPQL with index params")
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployee() {
        //given - precondition
        Employee employee = Employee.builder()
                .firstName("Luis Jair")
                .lastName("Lopez Murillo")
                .email("luizz.jair@gmail.com")
                .build();
        employeeRepository.save(employee);
        String firstName = "Luis Jair";
        String lastName = "Lopez Murillo";

        //when - action or behavior
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }


}
