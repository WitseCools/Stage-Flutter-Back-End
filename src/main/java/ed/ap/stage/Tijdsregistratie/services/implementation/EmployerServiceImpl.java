package ed.ap.stage.Tijdsregistratie.services.implementation;

import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.repositories.EmployerRepository;
import ed.ap.stage.Tijdsregistratie.services.AbstractService;
import ed.ap.stage.Tijdsregistratie.services.EmployerService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class EmployerServiceImpl  extends AbstractService<Employer, UUID> implements EmployerService {

    EmployerRepository repository;

    public EmployerServiceImpl(EmployerRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CrudRepository<Employer, UUID> getRepository() {
        return repository;
    }

    @Override
    public Employer CreateEmployer(String employerName, Date date) {
       Employer employer = Employer.builder()
               .name(employerName)
               .date(date)
               .build();


       return repository.save(employer);
    }
}
