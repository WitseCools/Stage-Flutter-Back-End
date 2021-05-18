package ed.ap.stage.Tijdsregistratie.services.implementation;

import ed.ap.stage.Tijdsregistratie.entities.*;
import ed.ap.stage.Tijdsregistratie.repositories.TimeLogRepository;
import ed.ap.stage.Tijdsregistratie.services.TaskService;
import ed.ap.stage.Tijdsregistratie.services.TimeLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TimelogServiceImplTest {


    TimelogServiceImpl timeLogService;

    @Mock
    UserServiceImpl userService;
    @Mock
    TaskServiceImpl taskService;
    @Mock
    LocationServiceImpl locationService;
    @Mock
    TimeLogCatServiceImpl timeLogCatService;

    @Mock
    TimeLogRepository timeLogRepository;

    Employee employee;
    Task task;
    Location location;
    TimelogCat timelogCat;


    @BeforeEach
    private void setup() {
        timeLogService = new TimelogServiceImpl(timeLogRepository, userService, taskService, locationService, timeLogCatService);


        employee = Employee.builder().employeeId(UUID.fromString("f0ae0566-93ba-11eb-a8b3-0242ac130003")).lastName("Test").firstName("Test2").build();
        task = Task.builder().taskId(UUID.fromString("f0ae0566-93ba-11eb-a8b3-0242ac130003")).build();
        location = Location.builder().locationId(UUID.fromString("f0ae0566-93ba-11eb-a8b3-0242ac130003")).build();
        timelogCat = TimelogCat.builder().timelogCatId(UUID.fromString("f0ae0566-93ba-11eb-a8b3-0242ac130003")).name("Consultant").build();


        Mockito.when(userService.getById(employee.getEmployeeId())).thenReturn(employee);
       // Mockito.when(taskService.getById(task.getTaskId())).thenReturn(task);
//        Mockito.when(locationService.getById(location.getLocationId())).thenReturn(location);
        Mockito.when(timeLogCatService.getById(timelogCat.getTimelogCatId())).thenReturn(timelogCat);
    }


    @Test
    void createTimeLog() {

        timeLogService.CreateTimeLog(Timestamp.valueOf("2021-04-01 20:30:55.888"), Timestamp.valueOf("2021-04-01 21:30:55.888"), Timestamp.valueOf("2021-04-01 22:30:55.888"), Timestamp.valueOf("2021-04-01 23:30:55.888"), employee.getEmployeeId(), task.getTaskId(), location.getLocationId(), timelogCat.getTimelogCatId(), null, 10);

        ArgumentCaptor<Timelog> captor = ArgumentCaptor.forClass(Timelog.class);
        Mockito.verify(timeLogRepository).save(captor.capture());
        Timelog timelog = captor.getValue();

        assertEquals(Timestamp.valueOf("2021-04-01 20:30:55.888"), timelog.getStartAM());
        assertEquals(Timestamp.valueOf("2021-04-01 21:30:55.888"), timelog.getStartPM());
        assertEquals(Timestamp.valueOf("2021-04-01 22:30:55.888"), timelog.getStopAM());
        assertEquals(Timestamp.valueOf("2021-04-01 23:30:55.888"), timelog.getStopPM());
        assertEquals(employee.getEmployeeId(), timelog.getEmployee().getEmployeeId());
        assertEquals(task.getTaskId(),timelog.getTask().getTaskId());
        assertEquals(location.getLocationId(), timelog.getLocation().getLocationId());
        assertEquals(timelogCat.getTimelogCatId(), timelog.getTimelogCat().getTimelogCatId());
    }


    @Test
    void createTimeLogVacation() {
        timeLogService.CreateTimeLogVacation(Timestamp.valueOf("2021-04-01 20:30:55.888"), Timestamp.valueOf("2021-04-01 21:30:55.888"),employee.getEmployeeId(),timelogCat.getTimelogCatId());

        ArgumentCaptor<Timelog> captor = ArgumentCaptor.forClass(Timelog.class);
        Mockito.verify(timeLogRepository).save(captor.capture());
        Timelog timelog = captor.getValue();

        assertEquals(Timestamp.valueOf("2021-04-01 20:30:55.888"), timelog.getStartAM());
        assertEquals(Timestamp.valueOf("2021-04-01 21:30:55.888"), timelog.getStopPM());
        assertEquals(employee.getEmployeeId(), timelog.getEmployee().getEmployeeId());
        assertEquals(timelogCat.getTimelogCatId(), timelog.getTimelogCat().getTimelogCatId());

    }

    @Test
    void update() {
    }
}