package ed.ap.stage.Tijdsregistratie.repositories;

import com.sun.xml.bind.v2.TODO;
import ed.ap.stage.Tijdsregistratie.entities.*;
import ed.ap.stage.Tijdsregistratie.enums.EmployeeRole;
import ed.ap.stage.Tijdsregistratie.statistics.IAverageTask;
import ed.ap.stage.Tijdsregistratie.statistics.ITotalForProject;
import ed.ap.stage.Tijdsregistratie.statistics.ITotalHoursForEachTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TimeLogRepositoryTest {

    @Autowired
    TimeLogRepository repository;

    @Autowired
    TestEntityManager manager;

    Task task20;
    Task task40;
    Timelog timelog1;
    Timelog timelog2;
    Timelog timelog3;
    TimelogCat timelogCat;
    EmployeeType employeeTypeCons;
    Set<EmployeeType> employeeTypeList;
    Project project;
    Employer employer;
    Employee employee;

    @BeforeEach
    void setup(){
         task20 = Task.builder()
                .name("Test20")
                .build();

         task40 = Task.builder()
                .name("Test40")
                .build();

         timelog1 = Timelog.builder()
                .task(task20)
                .total(20)
                .build();
         timelog2 = Timelog.builder()
                .task(task40)
                .total(10)
                .build();
         timelog3 = Timelog.builder()
                .task(task40)
                .total(30)
                .build();

        task20 = manager.persist(task20);
        task40 = manager.persist(task40);

        timelog1 = manager.persist(timelog1);
        timelog2 = manager.persist(timelog2);
        timelog3 = manager.persist(timelog3);
         timelogCat = TimelogCat.builder()

                .name("Working")
                .build();
    }

    @Test
    void getTimelogsByMonth() {

        Date date = new Date(2021,05,31);
        Date period = new Date(2022,05,31);

        employeeTypeCons = EmployeeType.builder()
                .employeeRole(EmployeeRole.consultant)
                .build();

        employeeTypeList = new HashSet<>();

        employeeTypeList.add(employeeTypeCons);



         employee = Employee.builder()
                .employee_type(employeeTypeList)
                .firstName("Jan")
                .lastName("Man")
                .function("Java")
                .active(true)
                .email("JanMan@hotmail.com")
                .password("azerty")

                .build();

         employer = Employer.builder()

                .date(date)
                .name("Test klant")
                .build();

         project = Project.builder()
                .startDate(date)
                .employer(employer)

                .name("Project 1")
                .employee(employee)
                .build();

        Task task1 = Task.builder()
                .project(project)
                .name("Task1")
                .build();

        Location location = Location.builder()
                .employer(employer)
                .radius(25)
                .locationLon(50.123456f)
                .locationLat(50.123456f)
                .locationName("Brussel")

                .build();

        Timelog timelog1 = Timelog.builder()
                .startAM(Timestamp.valueOf("2021-09-23 10:10:10"))
                .stopAM(Timestamp.valueOf("2021-09-23 12:10:10"))
                .startPM(Timestamp.valueOf("2021-09-23 14:10:10"))
                .stopPM(Timestamp.valueOf("2021-09-23 18:10:10"))
                .task(task1)
                .employee(employee)
                .timelogCat(timelogCat)
                .location(location)
                .date(date)
                .total(20)
                .build();


        project = manager.persist(project);
        employeeTypeCons = manager.persist(employeeTypeCons);
        timelogCat = manager.persist(timelogCat);
        employee = manager.persist(employee);
        employer = manager.persist(employer);
        task1 = manager.persist(task1);
        location = manager.persist(location);
        timelog1 = manager.persist(timelog1);

        Date startDate = new Date(2021, Calendar.JUNE,1);
        Date endDate = new Date(2021, Calendar.JULY,31);

        Date startDate2 = new Date(2022, Calendar.JUNE,1);
        Date endDate2 = new Date(2022, Calendar.JULY,31);

        double result = repository.getTimelogsByMonth(employee.getEmployeeId() , startDate , endDate);
        //double result2 = repository.getTimelogsByMonth(employee.getEmployeeId() , startDate2 , endDate2);

        assertEquals( result , 20.0);
        //assertEquals( result2 , null);
    }

    @Test
    void getTotalForEachTask() {
        /* task20 = Task.builder()
                .name("Test20")
                .build();
         task40 = Task.builder()
                .name("40")
                .build();

         timelog1 = Timelog.builder()
                .task(task20)
                .total(20)
                .build();
         timelog2 = Timelog.builder()
                .task(task40)
                .total(10)
                .build();
         timelog3 = Timelog.builder()
                .task(task40)
                .total(30)
                .build();*/

       /* task20 = manager.persist(task20);
        task40 = manager.persist(task40);

        timelog1 = manager.persist(timelog1);
        timelog2 = manager.persist(timelog2);
        timelog3 = manager.persist(timelog3);*/

        List<ITotalHoursForEachTask> result  = repository.getTotalForEachTask();

        assertEquals(40,result.get(0).getTotaal());
        assertEquals(20,result.get(1).getTotaal());
    }

    @Test
    void getTotalByProject() {

        Project project = Project.builder()
                .name("Project1")
                .build();

        Project project2 = Project.builder()

                .name("Project2")
                .build();

        Task task20 = Task.builder()
                .name("Test20")
                .project(project)
                .build();
        Task task40 = Task.builder()
                .project(project2)
                .name("40")
                .build();

        Timelog timelog1 = Timelog.builder()
                .task(task20)
                .total(20)
                .build();

        Timelog timelog2 = Timelog.builder()
                .task(task40)
                .total(10)
                .build();

        Timelog timelog3 = Timelog.builder()
                .task(task40)
                .total(30)
                .build();

        task20 = manager.persist(task20);
        task40 = manager.persist(task40);

        timelog1 = manager.persist(timelog1);
        timelog2 = manager.persist(timelog2);
        timelog3 = manager.persist(timelog3);

        project = manager.persist(project);
        project2 = manager.persist(project2);

        List<ITotalForProject> resultProject1 = repository.getTotalByProject(project.getProjectId());
        List<ITotalForProject> resultProject2 = repository.getTotalByProject(project2.getProjectId());

        assertEquals(resultProject1.get(0).getProject_name() , "Project1" );
        assertEquals(resultProject2.get(0).getProject_name() , "Project2" );
        assertEquals(20,resultProject1.get(0).getTotaal());
        assertEquals(40,resultProject2.get(0).getTotaal());


    }

    @Test
    void getTotalByProjectForEmployee() {

        Employee employee1 = Employee.builder()
                .firstName("Employee1")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Employee2")
                .build();

        Project project1 = Project.builder()
                .name("Project1")
                .employee(employee1)

                .build();

        Project project2 = Project.builder()
                .name("Project2")
                .employee(employee2)
                .build();

        Task task20 = Task.builder()
                .name("Test20")
                .project(project1)
                .build();

        Task task40 = Task.builder()
                .project(project2)
                .name("40")
                .build();

        Timelog timelog1 = Timelog.builder()
                .employee(employee1)
                .task(task20)
                .total(20)
                .build();

        Timelog timelog2 = Timelog.builder()
                .employee(employee2)
                .task(task40)
                .total(10)
                .build();

        Timelog timelog3 = Timelog.builder()
                .employee(employee2)
                .task(task40)
                .total(30)
                .build();



        task20 = manager.persist(task20);
        task40 = manager.persist(task40);

        employee1 = manager.persist(employee1);
        employee2 = manager.persist(employee2);

        project1 = manager.persist(project1);
        project2 = manager.persist(project2);

        timelog1 = manager.persist(timelog1);
        timelog2 = manager.persist(timelog2);
        timelog3 = manager.persist(timelog3);


        List<ITotalForProject> resultEmployee1 = repository.getTotalByProjectForEmployee(employee1.getEmployeeId());
        List<ITotalForProject> resultEmployee2 = repository.getTotalByProjectForEmployee(employee2.getEmployeeId());

        assertEquals(resultEmployee1.get(0).getProject_name() , "Project1" );
        assertEquals(resultEmployee2.get(0).getProject_name() , "Project2" );
        assertEquals(employee1.getFirstName(), "Employee1");
        assertEquals(employee2.getFirstName(), "Employee2");
        assertEquals(resultEmployee1.get(0).getTotaal() , 20 );
        assertEquals(resultEmployee2.get(0).getTotaal() , 40 );
    }

    @Test
    void getTotalByProjects() {

        Project project1 = Project.builder()
                .name("Project1")
                .build();

        Project project2 = Project.builder()
                .name("Project2")
                .build();

        Task task20 = Task.builder()
                .name("Test20")
                .project(project1)
                .build();

        Task task40 = Task.builder()
                .project(project2)
                .name("40")
                .build();

        Timelog timelog1 = Timelog.builder()
                .task(task20)
                .total(20)
                .build();

        Timelog timelog2 = Timelog.builder()
                .task(task40)
                .total(10)
                .build();

        Timelog timelog3 = Timelog.builder()
                .task(task40)
                .total(30)
                .build();

        task20 = manager.persist(task20);
        task40 = manager.persist(task40);

        project1 = manager.persist(project1);
        project2 = manager.persist(project2);

        timelog1 = manager.persist(timelog1);
        timelog2 = manager.persist(timelog2);
        timelog3 = manager.persist(timelog3);


        List<ITotalForProject> resultProjects = repository.getTotalByProjects();


        assertEquals(resultProjects.get(0).getProject_name() , "Project1");
        assertEquals(resultProjects.get(1).getProject_name() , "Project2");
        assertEquals(resultProjects.get(0).getTotaal() , 20);
        assertEquals(resultProjects.get(1).getTotaal() , 40);
    }

    @Test
    void getAverageTaskTime() {

        List<IAverageTask> result = repository.getAverageTaskTime();

        assertEquals("Test20" , result.get(0).getName() );
        assertEquals("Test40",result.get(1).getName()  );
        assertEquals(20,result.get(0).getAverage() );
        assertEquals(20 ,result.get(1).getAverage()  );
    }

    @Test
    void getEmployeeVacation() {
        Date startDate = new Date(2021, 6,1);;
        Date inBetween = new Date(2021,6 , 5);
        Date endDate = new Date(2021, 7,31);
        Date outOfDate = new Date(2021,8 , 5);

        Employee employee = Employee.builder()
                .firstName("Employee1")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Employee2")
                .build();

        employee = manager.persist(employee);
        employee2 = manager.persist(employee2);

        TimelogCat timelogCat = TimelogCat.builder()
                .name("Day_off")
                .build();


        Timelog timelog1 = Timelog.builder()
                .employee(employee)
                .date(inBetween)
                .timelogCat(timelogCat)
                .build();

        Timelog timelog2 = Timelog.builder()
                .employee(employee)
                .date(outOfDate)
                .timelogCat(timelogCat)
                .build();

        Timelog timelog3 = Timelog.builder()
                .employee(employee2)
                .date(outOfDate)
                .timelogCat(timelogCat)
                .build();

        Timelog timelog4 = Timelog.builder()
                .employee(employee2)
                .date(inBetween)
                .timelogCat(timelogCat)
                .build();

        Timelog timelog5 = Timelog.builder()
                .employee(employee2)
                .date(inBetween)
                .timelogCat(timelogCat)
                .build();


        timelogCat = manager.persist(timelogCat);
        timelog1 = manager.persist(timelog1);
        timelog2 = manager.persist(timelog2);
        timelog3 = manager.persist(timelog3);
        timelog4 = manager.persist(timelog4);
        timelog5 = manager.persist(timelog5);




        List<Timelog> timelogsEmployee1 = repository.getEmployeeVacation(employee.getEmployeeId(), startDate ,endDate);
        List<Timelog> timelogsEmployee2 = repository.getEmployeeVacation(employee2.getEmployeeId(), startDate ,endDate);



        assertEquals(1,timelogsEmployee1.size());
        assertEquals( 2, timelogsEmployee2.size());


    }

    @Test
    void getTimesheetForAProject() {
        Project project = Project.builder().build();
        Task task = Task.builder().project(project).build();
        Task task2 = Task.builder().project(project).build();
        Timelog timelog = Timelog.builder()
                .task(task).build();
        Timelog timelog2 = Timelog.builder().task(task).build();
        Timelog timelog3 = Timelog.builder().task(task2).build();
        project =manager.persist(project);

        task = manager.persist(task);
        task2 = manager.persist(task2);
        timelog = manager.persist(timelog);
        timelog2 = manager.persist(timelog2);
        timelog3 = manager.persist(timelog3);

        List<Timelog> result = repository.getTimesheetForAProject(project.getProjectId());

        assertEquals(3 , result.size());


    }

    @Test
    void findTimelogByEmployee() {
        Date startDate = new Date(2021, Calendar.JUNE,1);
        Date inBetween = new Date(2021,Calendar.JUNE , 5);
        Date endDate = new Date(2021, Calendar.JULY,31);
        Date outOfDate = new Date(2021,Calendar.AUGUST , 5);

        Employee employee = Employee.builder()
                .firstName("Employee1")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Employee2")
                .build();


        Timelog timelog1 = Timelog.builder()
                .employee(employee)
                .date(inBetween)
                .build();

        Timelog timelog2 = Timelog.builder()
                .employee(employee)
                .date(outOfDate)
                .build();

        Timelog timelog3 = Timelog.builder()
                .employee(employee2)
                .date(outOfDate)
                .build();

        Timelog timelog4 = Timelog.builder()
                .employee(employee2)
                .date(inBetween)
                .build();

        Timelog timelog5 = Timelog.builder()
                .employee(employee2)
                .date(inBetween)
                .build();

        employee = manager.persist(employee);
        employee2 = manager.persist(employee2);
        timelog1 = manager.persist(timelog1);
        timelog2 = manager.persist(timelog2);
        timelog3 = manager.persist(timelog3);
        timelog4 = manager.persist(timelog4);
        timelog5 = manager.persist(timelog5);

        List<Timelog> result = repository.findTimelogByEmployee(employee.getEmployeeId(), startDate , endDate);
        List<Timelog> result2 = repository.findTimelogByEmployee(employee2.getEmployeeId(), startDate , endDate);

        assertEquals(1 , result.size());
        assertEquals( 2, result2.size());


    }

    @Test
    void findTimelogsByTaskTaskIdIn() {

        Task task20 = Task.builder()
                .name("Test20")
                .build();

        Task task40 = Task.builder()
                .name("Test40")
                .build();


        Timelog timelog1 = Timelog.builder()
                .task(task20)
                .build();

        Timelog timelog2 = Timelog.builder()
                .task(task20)
                .build();

        Timelog timelog3 = Timelog.builder()
                .task(task20)
                .build();

        Timelog timelog4 = Timelog.builder()
                .task(task20)
                .build();

        Timelog timelog5 = Timelog.builder()
                .task(task40)
                .build();

        timelog1 = manager.persist(timelog1);
        timelog2 = manager.persist(timelog2);
        timelog3 = manager.persist(timelog3);
        timelog4 = manager.persist(timelog4);
        timelog5 = manager.persist(timelog5);

        task20 = manager.persist(task20);
        task40 = manager.persist(task40);

        List<UUID> tasks = new ArrayList<>();

        tasks.add(task20.getTaskId());
        tasks.add(task40.getTaskId());
        List<Timelog> timelogs = repository.findTimelogsByTaskTaskIdIn(tasks);

        assertEquals(5,timelogs.size());
        assertNotNull(timelogs);
    }

    @Test
    void findTimelogsByEmployeeEmployeeId() {

        TimelogCat timelogCat = TimelogCat.builder()
                .name("Working").build();

        TimelogCat timelogCatOff = TimelogCat.builder()
                .name("Day_off").build();

        Employee employee = Employee.builder()
                .build();

        Employee employee2 = Employee.builder()
                .build();

        Timelog timelog1 = Timelog.builder()
                .timelogCat(timelogCat)
                .employee(employee)
                .build();

        Timelog timelog2 = Timelog.builder()
                .timelogCat(timelogCat)
                .employee(employee)
                .build();

        Timelog timelog3 = Timelog.builder()
                .timelogCat(timelogCat)
                .employee(employee2)
                .build();

        Timelog timelog4 = Timelog.builder()
                .timelogCat(timelogCat)
                .employee(employee2)
                .build();

        Timelog timelog5 = Timelog.builder()
                .timelogCat(timelogCatOff)
                .employee(employee2)
                .build();

        timelog1 = manager.persist(timelog1);
        timelog2 = manager.persist(timelog2);
        timelog3 = manager.persist(timelog3);
        timelog4 = manager.persist(timelog4);
        timelog5 = manager.persist(timelog5);

        employee = manager.persist(employee);
        employee2 = manager.persist(employee2);

        timelogCat = manager.persist(timelogCat);
        timelogCatOff = manager.persist(timelogCatOff);


        List<Timelog> result = repository.findTimelogsByEmployeeEmployeeId(employee.getEmployeeId());
        List<Timelog> result2 = repository.findTimelogsByEmployeeEmployeeId(employee2.getEmployeeId());

        assertEquals(2 , result.size());
        assertEquals(2, result2.size());

    }

    @Test
    void findTimelogsByTaskTaskIdInAndDate() {
        Date dateToCheck = new Date(2021, Calendar.JULY,31);
        Date dateChecker = new Date(2021, Calendar.JULY,31);
        Date dateOver = new Date(2021,Calendar.AUGUST , 5);


        Task task20 = Task.builder()
                .name("Test20")
                .build();

        Task task40 = Task.builder()
                .name("Test40")
                .build();


        Timelog timelog1 = Timelog.builder()
                .task(task20)
                .date(dateToCheck)
                .build();

        Timelog timelog2 = Timelog.builder()
                .task(task20)
                .date(dateToCheck)
                .build();

        Timelog timelog3 = Timelog.builder()
                .task(task20)
                .date(dateOver)
                .build();

        Timelog timelog4 = Timelog.builder()
                .task(task20)
                .date(dateToCheck)
                .build();

        Timelog timelog5 = Timelog.builder()
                .task(task40)
                .date(dateOver)
                .build();

        timelog1 = manager.persist(timelog1);
        timelog2 = manager.persist(timelog2);
        timelog3 = manager.persist(timelog3);
        timelog4 = manager.persist(timelog4);
        timelog5 = manager.persist(timelog5);

        task20 = manager.persist(task20);
        task40 = manager.persist(task40);

        List<UUID> tasks = new ArrayList<>();

        tasks.add(task20.getTaskId());
        tasks.add(task40.getTaskId());

        List<Timelog> result = repository.findTimelogsByTaskTaskIdInAndDate(tasks,dateChecker);

        assertNotNull(result);

        assertEquals(3 , result.size());



    }
}