package ed.ap.stage.Tijdsregistratie.services.implementation;

import ed.ap.stage.Tijdsregistratie.entities.Employee;
import ed.ap.stage.Tijdsregistratie.repositories.EmployeeRepository;
import ed.ap.stage.Tijdsregistratie.services.AbstractService;
import ed.ap.stage.Tijdsregistratie.services.UserService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl extends AbstractService<Employee, UUID> implements UserService {

    private final EmployeeRepository employeeRepository;

    public UserServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    protected CrudRepository<Employee, UUID> getRepository() {
        return employeeRepository;
    }

}
