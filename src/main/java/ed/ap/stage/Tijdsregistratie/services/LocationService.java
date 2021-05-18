package ed.ap.stage.Tijdsregistratie.services;

import ed.ap.stage.Tijdsregistratie.dto.LocationDto;
import ed.ap.stage.Tijdsregistratie.dto.TimelogDto;
import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.entities.Location;
import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.entities.Timelog;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface LocationService extends BaseService<Location, UUID> {

    List<Location> getLocationsByEmployer(UUID id);

    List<Location> getByProjectId(UUID employerId);

    Location CreateLocation(String name , float latitude, float longitude, int radius , UUID employerId);
    default Location CreateLocation(LocationDto location){
        return CreateLocation(location.getLocationName(),location.getLocationLat(),location.getLocationLon(),location.getRadius(),location.getEmployerId());
    }

}
