package ed.ap.stage.Tijdsregistratie.controller;


import ed.ap.stage.Tijdsregistratie.dto.TimelogDto;
import ed.ap.stage.Tijdsregistratie.annotations.Dto;
import ed.ap.stage.Tijdsregistratie.entities.Timelog;
import ed.ap.stage.Tijdsregistratie.repositories.TimeLogRepository;
import ed.ap.stage.Tijdsregistratie.services.TimeLogService;
import ed.ap.stage.Tijdsregistratie.statistics.IAverageTask;
import ed.ap.stage.Tijdsregistratie.statistics.ITotalForProject;
import ed.ap.stage.Tijdsregistratie.statistics.ITotalHoursForEachTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/timesheet")
public class TimelogController {

    @Autowired
    private TimeLogService timeLogService;

    @Autowired
    private TimeLogRepository repository;

    @PostMapping(value="/add" , consumes = "application/json")
    @Dto(TimelogDto.class)
    public ResponseEntity<?> AddTimeLog(@RequestBody TimelogDto log) throws Exception {

        Timelog t = timeLogService.CreateTimeLog(log);

        return ResponseEntity
                .created(
                        new URI("/api/timesheet" + t.getTimelogId())
                )
                .body(t);
    }

    @PostMapping(value ="/addVacation" , consumes = "application/json")
    @Dto(TimelogDto.class)
    public ResponseEntity<?> AddTimeLogVacation(@RequestBody TimelogDto log) throws Exception {

        Timelog t = timeLogService.CreateTimeLogVacation(log);

        return ResponseEntity
                .created(
                        new URI("/api/timesheet" + t.getTimelogId())
                )
                .body(t);
    }

    @GetMapping("/get")
    @Dto(TimelogDto.class)
    public List<Timelog> GetByEmployeeID(@RequestParam UUID employeeId,@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return timeLogService.GetByEmployeeId(employeeId,startDate,endDate);
    }

    @GetMapping("/getting")
    @Dto(TimelogDto.class)
    public List<Timelog> GetByMineEmployeeId(@RequestParam UUID employeeId) {
        return timeLogService.GetMyTimelogs(employeeId);
    }

    @GetMapping("/getByProject")
    @Dto(TimelogDto.class)
    public List<Timelog> getByProject(@RequestParam UUID projectId) {
        return timeLogService.GetTimelogsFromProject(projectId);
    }

    @GetMapping("/vacation")
    @Dto(TimelogDto.class)
    public List<Timelog> getVacationDays(@RequestParam UUID employeeId,@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return timeLogService.GetVacationByEmployee(employeeId,startDate,endDate);
    }

    @GetMapping("/")
    @Dto(TimelogDto.class)
    public List<Timelog> Index() {
        return timeLogService.getAll();
    }

    @GetMapping("/project/{id}")
    @Dto(TimelogDto.class)
    public List<Timelog> GetByProject(@PathVariable("id") UUID id, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date) {
        if (date.isPresent()) {
            return timeLogService.GetByProjectIdAndDate(id, date.get());
        }
        return timeLogService.GetByProjectId(id);
    }

    @GetMapping("/total")
    public double GetTimesheetTotal(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(required = false) UUID employeeId

    ) {


            return timeLogService.GetTimelogsTotal(employeeId, startDate, endDate);



    }

    @GetMapping("/all/my")
    public List<ITotalForProject> GetTotalByEmployeeId(
            @RequestParam(required = false) Optional<UUID> employeeId
    ) {
        return timeLogService.GetSumOfHoursByEmployee(employeeId.get());
    }

    @GetMapping("/calculate")
    public List<ITotalForProject> GetTimesheetTotalForProject(

            @RequestParam(required = false) Optional<UUID> projectId
    ) {
        return timeLogService.GetSumOfHoursProject(projectId.get());


    }

    @GetMapping("/avg")
    public List<IAverageTask> getAverageTime() {
        List<IAverageTask> averageTasks = timeLogService.GetAverageForEachTask();
        return averageTasks;
    }

    @GetMapping("/totalProjects")
    public List<ITotalForProject> getTotalForAllProjects() {
        List<ITotalForProject> totalProjects = timeLogService.GetTotalHoursForEachProject();
        return totalProjects;
    }

    @GetMapping("/totalEachTask")
    public List<ITotalHoursForEachTask> getTotalEachTask() {
        List<ITotalHoursForEachTask> totalEachTask = timeLogService.GetTotalHoursForEachTask();
        return totalEachTask;
    }

    @PutMapping(value = "/{id}")
    @Dto(TimelogDto.class)
    public ResponseEntity<?> updateDefect(@PathVariable("id") UUID id, @RequestBody TimelogDto timelog) {
        timeLogService.Update(id, timelog);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Dto(TimelogDto.class)
    public Timelog GetById(@PathVariable("id") UUID id) {
        Timelog t = timeLogService.getById(id);
        return t;
    }


}
