package ed.ap.stage.Tijdsregistratie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ed.ap.stage.Tijdsregistratie.dto.TimelogDto;
import ed.ap.stage.Tijdsregistratie.entities.*;
import ed.ap.stage.Tijdsregistratie.services.TimeLogService;
import ed.ap.stage.Tijdsregistratie.statistics.ITotalHoursForEachTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TimelogControllerTest {

    UUID timelog1Id = UUID.fromString("b3d193fe-b25c-11eb-8529-0242ac130003");
    UUID timelog2Id = UUID.fromString("b3d193fe-b25c-11eb-8529-0242ac130004");
    UUID timelogVacayId = UUID.fromString("b3d193fe-b25c-11eb-8529-0242ac130004");
    UUID timelog22Id = UUID.fromString("b3d193fe-b25c-11eb-8529-0242ac130005");

    UUID projectId1 = UUID.fromString("b3d193fe-b25c-11eb-8529-0242ac130010");
    UUID projectId2 = UUID.fromString("b3d193fe-b25c-11eb-8529-0242ac130020");

    UUID task1Id = UUID.fromString("b3d193fe-b25c-11eb-8529-0242ac130030");
    UUID task2Id = UUID.fromString("b3d193fe-b25c-11eb-8529-0242ac130040");

    UUID employeeId = UUID.fromString("b3d193fe-b25c-11eb-8529-0242ac130031");
    UUID employee2Id = UUID.fromString("b3d193fe-b25c-11eb-8529-0242ac130042");

    UUID vacayId = UUID.fromString("f3d193fe-b25c-11eb-8529-0242ac130042");

    Timelog timelog1;
    Timelog timelog2;
    Timelog timelog22;

    Project project;
    Project project2;

    Task task1;
    Task task2;

    Employee employee;
    Employee employee2;

    TimelogDto timelogDto;
    TimelogDto timelogVacayDto;

    Date startDate;
    Date endDate;
    Date dateInBetween;
    Date dateOutside;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TimeLogService timeLogService;

    @BeforeEach()
    void setup(){


        final List<Timelog> timelogs = new ArrayList<Timelog>();
        final List<Timelog> timelogsVacay = new ArrayList<Timelog>();

        timelogDto = TimelogDto.builder().timelogId(timelog1Id).total(20).build();
        timelogVacayDto = TimelogDto.builder().timeLogCatId(vacayId).timelogId(timelog1Id).total(160).build();

        employee = Employee.builder().employeeId(employeeId).build();
        employee2 = Employee.builder().employeeId(employee2Id).build();

        project = Project.builder().projectId(projectId1).build();
        project2 = Project.builder().projectId(projectId2).build();

        task1 = Task.builder().project(project).taskId(task1Id).build();
        task2 = Task.builder().project(project2).taskId(task2Id).build();

        timelog1 = Timelog.builder().total(20).employee(employee).date(dateInBetween).task(task1).timelogId(timelog1Id).build();
        timelog2 = Timelog.builder().total(20).employee(employee2).date(dateInBetween).task(task2).timelogId(timelog2Id).build();
        timelog22 = Timelog.builder().total(20).employee(employee2).date(dateOutside).task(task2).timelogId(timelog22Id).build();


        startDate = new Date(2021,05,01);
        endDate = new Date(2021,05,31);

        dateInBetween= new Date(2021,05,15);
        dateOutside= new Date(2021,06,01);

        timelogsVacay.add(timelog1);
        timelogsVacay.add(timelog2);

        timelogs.add(timelog1);
        timelogs.add(timelog2);
        timelogs.add(timelog22);

        when(timeLogService.CreateTimeLog(timelogDto)).thenReturn(Timelog.builder().timelogId(UUID.fromString("b3d193fe-b25c-11eb-8529-0242ac130003")).total(20).build());
        when(timeLogService.CreateTimeLogVacation(timelogVacayDto)).thenReturn(Timelog.builder().timelogId(UUID.fromString("f3d193fe-b25c-11eb-8529-0242ac130042")).total(160).build());
        when(timeLogService.GetByEmployeeId(employee.getEmployeeId(),startDate,endDate)).thenReturn(timelogs);
        when(timeLogService.getById(timelog1.getTimelogId())).thenReturn(timelog1);
        when(timeLogService.getAll()).thenReturn(timelogs);
        when(timeLogService.GetVacationByEmployee(employeeId,startDate,endDate)).thenReturn(timelogsVacay);
        when(timeLogService.GetByProjectId(project.getProjectId())).thenReturn(timelogs);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();


    }

    @Test
    void addTimeLog() throws Exception {
        this.mockMvc.perform(post("/api/timesheet/add")
                .content(asJsonString(this.timelogDto))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timelogId", is("b3d193fe-b25c-11eb-8529-0242ac130003")))
                .andExpect(jsonPath("$.total", is(this.timelogDto.getTotal())));
    }

    @Test
    void addTimeLogVacation() throws Exception {
        this.mockMvc.perform(post("/api/timesheet/addVacation")
                .content(asJsonString(this.timelogVacayDto))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timelogId", is("f3d193fe-b25c-11eb-8529-0242ac130042")))
                .andExpect(jsonPath("$.total", is(this.timelogVacayDto.getTotal())));
    }

    @Test
    void getByEmployeeID() throws Exception {
        this.mockMvc.perform(get("/api/timesheet/get")
                .param("employeeId" , "b3d193fe-b25c-11eb-8529-0242ac130031")
                .param("startDate","2021-05-01")
                .param("endDate","2021-05-31")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(3)))
                .andExpect(jsonPath("$[*].timelogId", containsInAnyOrder("b3d193fe-b25c-11eb-8529-0242ac130003")));
               // .andExpect(jsonPath("$[1].locationId", is("d20fa812-b00c-11eb-8529-0242ac130004")));
    }

    @Test
    void getByMineEmployeeId() throws Exception {
    }

    @Test
    void getByProject() throws Exception {
        this.mockMvc.perform(get("/api/timesheet/getByProject")
                .param("projectId", projectId1.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));

    }

    @Test
    void getVacationDays() throws Exception {
        this.mockMvc.perform(get("/api/timesheet/vacation")
                .param("employeeId",employee.getEmployeeId().toString())
                .param("startDate", "2021-05-01")
                .param("endDate", "2021-05-31")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)));
               // .andExpect(jsonPath("$.timelogId", is("b3d193fe-b25c-11eb-8529-0242ac130004")));
    }

    @Test
    void index() throws Exception {
        this.mockMvc.perform(get("/api/timesheet/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(3)))
                .andExpect(jsonPath("$[*].timelogId", containsInAnyOrder("b3d193fe-b25c-11eb-8529-0242ac130004","b3d193fe-b25c-11eb-8529-0242ac130003","b3d193fe-b25c-11eb-8529-0242ac130005")));
    }


    @Test
    void updateDefect() throws Exception {
    }

    @Test
    void getById() throws Exception {
        this.mockMvc.perform(get("/api/timesheet/b3d193fe-b25c-11eb-8529-0242ac130003")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timelogId", is("b3d193fe-b25c-11eb-8529-0242ac130003")))
                .andExpect(jsonPath("$.total", is(20)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}