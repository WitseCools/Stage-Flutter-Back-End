package ed.ap.stage.Tijdsregistratie.repositories;

import ed.ap.stage.Tijdsregistratie.entities.Employee;
import ed.ap.stage.Tijdsregistratie.payloads.SignUpRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    TestEntityManager manager;

    @Test
    void findByEmail() {
        Employee employee = Employee.builder()
                .email("test@hotmail.com")
                .build();

        employee = manager.persist(employee);

        Optional<Employee> foundEmployee = repository.findByEmail("test@hotmail.com");

        assertEquals(foundEmployee.isEmpty() , false);

        assertEquals(employee.getEmail() , foundEmployee.get().getEmail());
    }

    @Test
    void existsByEmail() {

        Employee employee = Employee.builder()
                .email("test@hotmail.com")
                .build();

        employee = manager.persist(employee);

        Boolean found = repository.existsByEmail("test@hotmail.com");
        Boolean found2 = repository.existsByEmail("test2@hotmail.com");
        assertNotNull(found);
        assertEquals(found , true);
        assertEquals(found2 , false);




    }
}