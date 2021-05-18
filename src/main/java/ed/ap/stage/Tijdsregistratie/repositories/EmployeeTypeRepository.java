package ed.ap.stage.Tijdsregistratie.repositories;


import ed.ap.stage.Tijdsregistratie.entities.EmployeeType;
import ed.ap.stage.Tijdsregistratie.enums.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, UUID> {

    Boolean existsByEmployeeRole(EmployeeRole role);

    EmployeeType findByEmployeeRole(EmployeeRole role);



}
