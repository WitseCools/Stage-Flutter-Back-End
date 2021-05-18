package ed.ap.stage.Tijdsregistratie.repositories;

import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.entities.Location;
import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LocationRepositoryTest {


    @Autowired
    LocationRepository repository;
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TestEntityManager manager;

    @Test
    void getLocationsByEmployer_EmployerId() {
        Employer employer = Employer.builder()
                .build();
        Location location = Location.builder()
                .employer(employer)
                .build();

        employer = manager.persist(employer);
        location = manager.persist(location);

        List<Location> locations = repository.getLocationsByEmployer_EmployerId(employer.getEmployerId());

        assertEquals(locations.size() , 1);
        assertNotEquals(locations , null);
        assertEquals(location.getEmployer() , employer);
    }

    @Test
    void getLocationsByEmployer_EmployerIdIn() {
        Employer employer = Employer.builder()
                .build();

        Project project = Project.builder()
                .employer(employer)
                .build();

        Location location = Location.builder()
                .employer(employer)
                .build();

        employer = manager.persist(employer);
        location = manager.persist(location);
        project = manager.persist(project);

        List<Project> projects = projectRepository.getProjectsByEmployer_EmployerId(employer.getEmployerId());



        assertEquals(projects.size() , 1);


    }
}