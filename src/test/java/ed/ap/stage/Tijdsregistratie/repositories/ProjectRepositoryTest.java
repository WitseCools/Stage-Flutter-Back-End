package ed.ap.stage.Tijdsregistratie.repositories;

import ed.ap.stage.Tijdsregistratie.entities.Employee;
import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.entities.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    ProjectRepository repository;

    @Autowired
    TestEntityManager manager;


    @Test
    void getProjectsByEmployee_EmployeeId() {
        Employee employee = Employee.builder()
                .build();


        Project project = Project.builder()
                .employee(employee)
                .build();


        Project project2 = Project.builder()
                .employee(employee)
                .build();

        employee = manager.persist(employee);
        project = manager.persist(project);
        project2 = manager.persist(project2);


        List<Project> projects = repository.getProjectsByEmployee_EmployeeId(employee.getEmployeeId());
        assertEquals(2, projects.size());
        assertEquals(project.getEmployee() , employee);
        assertEquals(project2.getEmployee() , employee);
        assertNotNull(project.getProjectId());
        assertNotNull(project2.getProjectId());
        assertNotNull(employee.getEmployeeId());


    }

    @Test
    void getProjectsByEmployer_EmployerId() {
        Employer employer = Employer.builder()
            .build();

        Project project = Project.builder()
                .employer(employer)
                .build();

        project = manager.persist(project);
        employer = manager.persist(employer);

        List<Project> projects = repository.getProjectsByEmployer_EmployerId(employer.getEmployerId());

        assertEquals(1 , projects.size());
        assertEquals(project.getEmployer() , employer);
        assertNotNull(projects);
        assertNotNull(employer);

    }
}