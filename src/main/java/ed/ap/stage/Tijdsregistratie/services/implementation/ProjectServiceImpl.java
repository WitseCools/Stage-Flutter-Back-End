package ed.ap.stage.Tijdsregistratie.services.implementation;

import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.entities.Timelog;
import ed.ap.stage.Tijdsregistratie.repositories.ProjectRepository;
import ed.ap.stage.Tijdsregistratie.services.AbstractService;
import ed.ap.stage.Tijdsregistratie.services.EmployerService;
import ed.ap.stage.Tijdsregistratie.services.ProjectService;
import ed.ap.stage.Tijdsregistratie.services.UserService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl extends AbstractService<Project , UUID> implements ProjectService {

    ProjectRepository repository;
    EmployerService employerService;
    UserService userService;

    public ProjectServiceImpl(ProjectRepository repository , EmployerService employerService, UserService userService) {
        this.repository = repository;
        this.employerService = employerService;
        this.userService = userService;
    }

    @Override
    protected CrudRepository<Project, UUID> getRepository() {
        return repository;
    }

    @Override
    public List<Project> getEmployeeProjects(UUID id) {
        return repository.getProjectsByEmployee_EmployeeId(id);
    }

    @Override
    public List<Project> getEmployerProjects(UUID id) {
        return repository.getProjectsByEmployer_EmployerId(id);
    }

    @Override
    public Project CreateProject(Date period, Date startDate, String projectName, UUID employerId) {
        Project project = Project.builder()
                .name(projectName)
                .period(period)
                .startDate(startDate)
                .employer(employerService.getById(employerId))
                .build();

        return repository.save(project);
    }

    @Override
    public Project Update(UUID id, UUID employeeId) {
        Project project = getById(id);
        if(employeeId != null)
            project.setEmployee(userService.getById(employeeId));
        Project updatedProject  = repository.save(project);

        return updatedProject;
    }

}
