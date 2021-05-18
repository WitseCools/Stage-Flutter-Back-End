package ed.ap.stage.Tijdsregistratie.services;

import ed.ap.stage.Tijdsregistratie.dto.EmployerDto;
import ed.ap.stage.Tijdsregistratie.dto.TimelogDto;
import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.entities.Timelog;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public interface EmployerService extends BaseService<Employer , UUID> {

    Employer CreateEmployer(String employerName , Date date);
    default Employer CreateEmployer(EmployerDto employerDto){
        return CreateEmployer(employerDto.getName(), employerDto.getDate());
    }
}
