package ed.ap.stage.Tijdsregistratie.services.implementation;

import ed.ap.stage.Tijdsregistratie.dto.ProjectDto;
import ed.ap.stage.Tijdsregistratie.entities.Employee;
import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.entities.Location;
import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.repositories.ProjectRepository;
import ed.ap.stage.Tijdsregistratie.services.EmployerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {



    ProjectServiceImpl projectService;

    @Mock
    ProjectRepository repository;

    @Mock
    EmployerServiceImpl employerService;

    @Mock
    UserServiceImpl employeeService;


    Employer employer;
    Employee employee;
    Project project;

    UUID employeeId = UUID.randomUUID();

    UUID employerId= UUID.randomUUID();

    UUID projectId = UUID.randomUUID();

    UUID updatedUUID = UUID.randomUUID();

    @BeforeEach
    void setup(){
    projectService = new ProjectServiceImpl(repository,employerService,employeeService);
        Date date = new Date(2021, 05, 28);

        project = Project.builder()
                .projectId(UUID.randomUUID())
                .employee(employee)
                .name("Test")
                .period(date)
                .build();

         employer = Employer.builder()
                .date(date)
                .name("")
                .employerId(employerId)
                .build();

        employee = Employee.builder()
                .employeeId(employeeId)
                .firstName("Test")
                .lastName("Last")
                .password("Azerty")
                .email("email@hotmail.com")
                .build();

        //Mockito.when(employeeService.getById(employee.getEmployeeId())).thenReturn(employee);
        Mockito.when(employerService.getById((employer.getEmployerId()))).thenReturn(employer);
        //Mockito.when(projectService.getById(project.getProjectId())).thenReturn(project);
       // Mockito.when(userService.getById(employee.getEmployeeId())).thenReturn(employee);
    }

    @Test
    void createProject() {
        Date date = new Date(2021, 06, 28);
        Date startDate = new Date(2021,05,12);
        projectService.CreateProject(date,startDate,"Project1",employer.getEmployerId());

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        Mockito.verify(repository).save(captor.capture());
        Project project = captor.getValue();

        assertEquals("Project1", project.getName());
        assertEquals(startDate, project.getStartDate());
        assertEquals(employerId, project.getEmployer().getEmployerId());
        assertEquals(date, project.getPeriod());
    }

    @Test
    void update() {

      /*  Date date = new Date(2021, 06, 28);
        Date startDate = new Date(2021,05,12);



        projectService.Update(project.getProjectId(), employee.getEmployeeId());

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        Mockito.verify(repository).save(captor.capture());
        Project project = captor.getValue();

        assertEquals("Test2", project.getName());
       /* assertEquals(startDate, project.getStartDate());
        assertEquals(updatedUUID, project.getEmployer().getEmployerId());
        assertEquals(date, project.getPeriod());*/
    }
}