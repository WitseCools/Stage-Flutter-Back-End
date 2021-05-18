package ed.ap.stage.Tijdsregistratie.repositories;

import ed.ap.stage.Tijdsregistratie.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByEmail( String email);

    Boolean existsByEmail(String email);



}
