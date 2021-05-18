package ed.ap.stage.Tijdsregistratie.services;

import ed.ap.stage.Tijdsregistratie.dto.LocationDto;
import ed.ap.stage.Tijdsregistratie.dto.ProjectDto;
import ed.ap.stage.Tijdsregistratie.dto.TimelogDto;
import ed.ap.stage.Tijdsregistratie.entities.Location;
import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.entities.Timelog;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ProjectService extends BaseService<Project, UUID> {

    List<Project> getEmployeeProjects(UUID id);

    List<Project> getEmployerProjects(UUID id);



    Project CreateProject(Date period, Date startDate, String projectName , UUID employerId);
    default Project CreateProject(ProjectDto project){
        return CreateProject(project.getPeriod(), project.getStartDate() , project.getName() , project.getEmployerId());
    }

    Project Update(UUID id , UUID employeeId);
    default Project Update( UUID id, ProjectDto project){
        return Update(id, project.getEmployeeId());
    }



}
