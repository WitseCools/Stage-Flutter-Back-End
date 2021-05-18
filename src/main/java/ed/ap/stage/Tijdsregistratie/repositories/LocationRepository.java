package ed.ap.stage.Tijdsregistratie.repositories;

import ed.ap.stage.Tijdsregistratie.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    List<Location> getLocationsByEmployer_EmployerId(UUID id);

    List<Location> getLocationsByEmployer_EmployerIdIn(List<UUID> ids);



}
