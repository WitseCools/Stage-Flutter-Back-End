package ed.ap.stage.Tijdsregistratie.repositories;

import ed.ap.stage.Tijdsregistratie.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project , UUID> , JpaSpecificationExecutor<Project> {


    List<Project> getProjectsByEmployee_EmployeeId(UUID id);

    List<Project> getProjectsByEmployer_EmployerId(UUID id);




}
