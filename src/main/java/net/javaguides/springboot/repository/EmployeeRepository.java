package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Is not necessary to use @Repository because JpaRepository uses this annotation internally
 * @see org.springframework.data.jpa.repository.JpaRepository
 * <Employee, Long> we receive and Employee object and return a Long because we use a long in the id param *
 */

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    //Custom query using JPQL with index parameters
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);


}
