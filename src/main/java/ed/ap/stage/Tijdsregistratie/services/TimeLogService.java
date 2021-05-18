package ed.ap.stage.Tijdsregistratie.services;

import ed.ap.stage.Tijdsregistratie.dto.TimelogDto;
import ed.ap.stage.Tijdsregistratie.entities.*;
import ed.ap.stage.Tijdsregistratie.statistics.IAverageTask;
import ed.ap.stage.Tijdsregistratie.statistics.ITotalForProject;
import ed.ap.stage.Tijdsregistratie.statistics.ITotalHoursForEachTask;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TimeLogService extends BaseService<Timelog, UUID> {

    Timelog CreateTimeLog(Timestamp startAM, Timestamp startPM, Timestamp stopAM, Timestamp stopPM, UUID employeeId, UUID taskId, UUID location, UUID timeLogCatId, Date date ,  int total);
    default Timelog CreateTimeLog(TimelogDto timelog){
        return CreateTimeLog(timelog.getStartAM(),timelog.getStartPM(), timelog.getStopAM(),timelog.getStopPM(), timelog.getEmployeeId() , timelog.getTaskId(), timelog.getLocationId()  , timelog.getTimeLogCatId() ,timelog.getDate(), timelog.getTotal());
    }

    Timelog CreateTimeLogVacation(Timestamp startAM , Timestamp stopPM, UUID employeeId, UUID timeLogCatId);
    default Timelog CreateTimeLogVacation(TimelogDto timelog){
        return CreateTimeLogVacation(timelog.getStartAM(),timelog.getStopPM(), timelog.getEmployeeId() , timelog.getTimeLogCatId());
    }

    Timelog Update(UUID id , Timestamp startAM, Timestamp startPM, Timestamp stopAM, Timestamp stopPM, UUID taskId, UUID location, int total );
    default Timelog Update( UUID id, TimelogDto timelog){
        return Update(id, timelog.getStartAM(), timelog.getStartPM(), timelog.getStopAM(), timelog.getStopPM() , timelog.getTaskId(), timelog.getLocationId() , timelog.getTotal());
    }


    double GetTimelogsTotal(UUID employee, Date startDate, Date endDate);

    List<ITotalHoursForEachTask> GetTotalHoursForEachTask();

    List<ITotalForProject> GetSumOfHoursProject(UUID projectId);

    List<ITotalForProject> GetSumOfHoursByEmployee(UUID employeeId);

    List<ITotalForProject> GetTotalHoursForEachProject();

    List<IAverageTask> GetAverageForEachTask();

    List<Timelog> GetVacationByEmployee(UUID employee ,Date startDate ,Date endDate);

    List<Timelog> GetTimelogsFromProject( UUID projectId );

    List<Timelog> findTimelogsByEmployeeAndDate(UUID employeeId ,Date startDate ,Date endDate);

    List<Timelog> GetTimelogsByEmployee(UUID employee);


    List<Timelog> GetMyTimelogs(UUID employeeId);

    List<Timelog> GetByEmployeeId(UUID employeeId, Date startDate, Date endDate);

    List<Timelog> GetByProjectId(UUID projectId);
    List<Timelog> GetByProjectIdAndDate(UUID projectId , Date date);


}
