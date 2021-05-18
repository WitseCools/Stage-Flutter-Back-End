package ed.ap.stage.Tijdsregistratie.repositories;

import ed.ap.stage.Tijdsregistratie.entities.TimelogCat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TimeLogCatRepository extends JpaRepository<TimelogCat, UUID> {

}
