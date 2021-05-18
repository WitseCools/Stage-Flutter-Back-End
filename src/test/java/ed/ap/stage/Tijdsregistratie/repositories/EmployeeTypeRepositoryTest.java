package ed.ap.stage.Tijdsregistratie.repositories;

import ed.ap.stage.Tijdsregistratie.entities.Employee;
import ed.ap.stage.Tijdsregistratie.entities.EmployeeType;
import ed.ap.stage.Tijdsregistratie.enums.EmployeeRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeTypeRepositoryTest {

    @Autowired
    EmployeeTypeRepository repository;
    @Autowired
    TestEntityManager manager;

    @Test
    void existsByEmployeeRole() {

        EmployeeType employeeTypeCon = EmployeeType
                .builder()
                .employeeRole(EmployeeRole.consultant)
                .build();

        EmployeeType employeeTypeMan = EmployeeType
                .builder()
                .employeeRole(EmployeeRole.manager)
                .build();

        Set<EmployeeType> employeeTypeSet = new HashSet<>();

        employeeTypeSet.add(employeeTypeCon);

        Employee employee = Employee
                .builder()
                .employee_type(employeeTypeSet)
                .build();


        employee = manager.persist(employee);
        employeeTypeCon = manager.persist(employeeTypeCon);
        employeeTypeMan = manager.persist(employeeTypeMan);



        boolean result = repository.existsByEmployeeRole(EmployeeRole.manager);
        boolean result2 = repository.existsByEmployeeRole(EmployeeRole.consultant);

        assertEquals(true, result);
        assertEquals(true, result2);

    }

    @Test
    void findByEmployeeRole() {
        EmployeeType employeeTypeCon = EmployeeType
                .builder()
                .employeeRole(EmployeeRole.consultant)
                .build();

        EmployeeType employeeTypeMan = EmployeeType
                .builder()
                .employeeRole(EmployeeRole.manager)
                .build();

        Set<EmployeeType> employeeTypeSet = new HashSet<>();

        employeeTypeSet.add(employeeTypeCon);

        Employee employee = Employee
                .builder()
                .employee_type(employeeTypeSet)
                .build();

        EmployeeType  result = repository.findByEmployeeRole(EmployeeRole.consultant);

        assertEquals(null, result);

    }
}