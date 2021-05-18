package ed.ap.stage.Tijdsregistratie.services.implementation;

import ed.ap.stage.Tijdsregistratie.entities.Task;
import ed.ap.stage.Tijdsregistratie.entities.Timelog;
import ed.ap.stage.Tijdsregistratie.repositories.TimeLogRepository;
import ed.ap.stage.Tijdsregistratie.services.*;
import ed.ap.stage.Tijdsregistratie.statistics.IAverageTask;
import ed.ap.stage.Tijdsregistratie.statistics.ITotalForProject;
import ed.ap.stage.Tijdsregistratie.statistics.ITotalHoursForEachTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TimelogServiceImpl extends AbstractService<Timelog, UUID> implements TimeLogService {


    private TimeLogRepository timeLogRepository;

    private UserService userService;
    private TaskService taskService;
    private LocationService locationService;
    private TimeLogCatService timelogCatService;

    public TimelogServiceImpl(TimeLogRepository timeLogRepository, UserService userService, TaskService taskService, LocationService locationService, TimeLogCatService timelogCatService) {
        this.timeLogRepository = timeLogRepository;
        this.userService = userService;
        this.taskService = taskService;
        this.locationService = locationService;
        this.timelogCatService = timelogCatService;
    }

    @Override
    protected CrudRepository<Timelog, UUID> getRepository() {
        return timeLogRepository;
    }

    @Override
    public Timelog CreateTimeLog(Timestamp startAM, Timestamp startPM, Timestamp stopAM, Timestamp stopPM, UUID employeeId, UUID taskId, UUID locationId, UUID timeLogCatId, Date date, int total) {

        Timelog timelog = Timelog.builder()
                .startAM(startAM)
                .startPM(startPM)
                .stopAM(stopAM)
                .stopPM(stopPM)
                .employee(userService.getById(employeeId))
                .task(taskService.getById(taskId))
                .location(locationService.getById(locationId))
                .timelogCat(timelogCatService.getById(timeLogCatId))
                .date(date)
                .total(total)
                .build();

        return timeLogRepository.save(timelog);
    }

    @Override
    public Timelog CreateTimeLogVacation(Timestamp startAM, Timestamp stopPM, UUID employeeId, UUID timeLogCatId) {
        Timelog timelog = Timelog.builder()
                .startAM(startAM)
                .stopPM(stopPM)
                .employee(userService.getById(employeeId))
                .timelogCat(timelogCatService.getById(timeLogCatId))
                .build();

        return timeLogRepository.save(timelog);
    }

    @Override
    public Timelog Update(UUID id, Timestamp startAM, Timestamp startPM, Timestamp stopAM, Timestamp stopPM, UUID taskId, UUID locationId, int total) {
        Timelog timelog = getById(id);
        if(startAM != null)
            timelog.setStartAM(startAM);
        if(startPM != null)
            timelog.setStartPM(startPM);
        if(stopAM != null)
            timelog.setStopAM(stopAM);
        if(stopPM != null)
            timelog.setStopPM(stopPM);
        if(taskId != null)
            timelog.setTask(taskService.getById(taskId));
        if(locationId != null)
            timelog.setLocation(locationService.getById(locationId));
            timelog.setTotal(total);
        Timelog updatedTimelog  = timeLogRepository.save(timelog);

        return updatedTimelog;
    }

    @Override
    public double GetTimelogsTotal(UUID employee, Date startDate, Date endDate) {
        return timeLogRepository.getTimelogsByMonth(employee, startDate, endDate);
    }

    @Override
    public List<ITotalHoursForEachTask> GetTotalHoursForEachTask() {
        return timeLogRepository.getTotalForEachTask();
    }

    @Override
    public List<ITotalForProject> GetSumOfHoursProject(UUID projectId) {
        return timeLogRepository.getTotalByProject(projectId);
    }

    @Override
    public List<ITotalForProject> GetSumOfHoursByEmployee(UUID employeeId) {
        return timeLogRepository.getTotalByProjectForEmployee(employeeId);
    }

    @Override
    public List<ITotalForProject> GetTotalHoursForEachProject() {
        return timeLogRepository.getTotalByProjects();
    }

    @Override
    public List<IAverageTask> GetAverageForEachTask() {
        return timeLogRepository.getAverageTaskTime();
    }

    @Override
    public List<Timelog> GetVacationByEmployee(UUID employee, Date startDate, Date endDate) {
        return timeLogRepository.getEmployeeVacation(employee, startDate,endDate);
    }

    @Override
    public List<Timelog> GetTimelogsFromProject(UUID projectId) {
        return timeLogRepository.getTimesheetForAProject(projectId);
    }

    @Override
    public List<Timelog> findTimelogsByEmployeeAndDate(UUID employeeId, Date startDate, Date endDate) {
        return timeLogRepository.findTimelogByEmployee(employeeId,startDate,endDate);
    }

    @Override
    public List<Timelog> GetTimelogsByEmployee(UUID employee) {
        return timeLogRepository.findTimelogsByEmployeeEmployeeId(employee);
    }


    @Override
    public List<Timelog> GetMyTimelogs(UUID employeeId) {
        return timeLogRepository.findTimelogsByEmployeeEmployeeId(employeeId);
    }

    @Override
    public List<Timelog> GetByEmployeeId(UUID employeeId, Date startDate, Date endDate) {
        return timeLogRepository.findTimelogByEmployee(employeeId, startDate, endDate);
    }


    @Override
    public List<Timelog> GetByProjectId(UUID projectId) {
        List<Task> projectTasks = taskService.getTasksByProject(projectId);
        return timeLogRepository.findTimelogsByTaskTaskIdIn(projectTasks.stream().map(t -> t.getTaskId() ).collect(Collectors.toList()));
    }

    @Override
    public List<Timelog> GetByProjectIdAndDate(UUID projectId, Date date) {
        List<Task> projectTasks = taskService.getTasksByProject(projectId);
        return timeLogRepository.findTimelogsByTaskTaskIdInAndDate(projectTasks.stream().map(t -> t.getTaskId() ).collect(Collectors.toList()), date);
    }




}
