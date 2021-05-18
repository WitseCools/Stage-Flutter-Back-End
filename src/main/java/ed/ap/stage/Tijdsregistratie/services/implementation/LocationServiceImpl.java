package ed.ap.stage.Tijdsregistratie.services.implementation;

import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.entities.Location;
import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.entities.Task;
import ed.ap.stage.Tijdsregistratie.repositories.LocationRepository;
import ed.ap.stage.Tijdsregistratie.repositories.ProjectRepository;
import ed.ap.stage.Tijdsregistratie.services.AbstractService;
import ed.ap.stage.Tijdsregistratie.services.EmployerService;
import ed.ap.stage.Tijdsregistratie.services.LocationService;
import ed.ap.stage.Tijdsregistratie.services.ProjectService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl extends AbstractService<Location, UUID> implements LocationService {

    LocationRepository repository;
    ProjectService projectService;
    EmployerService employerService;

    public LocationServiceImpl(LocationRepository repository , ProjectService projectRepository, EmployerService employerService) {
        this.repository = repository;
        this.projectService = projectRepository;
        this.employerService = employerService;
    }

    @Override
    protected CrudRepository<Location, UUID> getRepository() {
        return repository;
    }

    @Override
    public List<Location> getLocationsByEmployer(UUID id) {
        return repository.getLocationsByEmployer_EmployerId(id);
    }

    // methode naam wijzigen, bekijken of methode overbodig is.
    @Override
    public List<Location> getByProjectId(UUID employerId) {
        List<Project> locationEmployer = projectService.getEmployerProjects(employerId);
        return repository.getLocationsByEmployer_EmployerIdIn(locationEmployer.stream().map(t -> t.getEmployer().getEmployerId() ).collect(Collectors.toList()));
    }

    @Override
    public Location CreateLocation(String name, float latitude, float longitude, int radius , UUID employerId) {
        Location location = Location.builder()
                .locationName(name)
                .locationLat(latitude)
                .locationLon(longitude)
                .radius(radius)
                .employer(employerService.getById(employerId))
                .build();

        return repository.save(location);
    }


}
