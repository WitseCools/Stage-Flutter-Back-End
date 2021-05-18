package ed.ap.stage.Tijdsregistratie.repositories;

import ed.ap.stage.Tijdsregistratie.entities.Timelog;
import ed.ap.stage.Tijdsregistratie.statistics.IAverageTask;
import ed.ap.stage.Tijdsregistratie.statistics.ITotalForProject;
import ed.ap.stage.Tijdsregistratie.statistics.ITotalHoursForEachTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimeLogRepository extends JpaRepository<Timelog, UUID> {

      // Get total of hours from timelogs from a certain employee between different dates
      @Query(value = "select  sum(total) from Timelog o where o.employee_id = :employee and o.date between :startDate and :endDate" , nativeQuery = true)
      double getTimelogsByMonth(@Param("employee") UUID employee ,@Param("startDate") Date startDate ,@Param("endDate") Date endDate);

      // Get the total time for each task
      @Query(value = "select sum(total) totaal , b.name  from Timelog o INNER JOIN task b ON b.task_id = o.task_id group by b.name" , nativeQuery = true)
      List<ITotalHoursForEachTask> getTotalForEachTask();

      // Get the sum of total time for a specific project
      @Query(value = "SELECT sum(total) totaal, p.project_name FROM timelog o INNER JOIN  task d ON o.task_id = d.task_id INNER JOIN  project p ON d.project_id = p.project_id where p.project_id = :projectId group by p.project_name;", nativeQuery = true)
      List<ITotalForProject> getTotalByProject(@Param("projectId") UUID projectId );

      // Get the sum of worked hours for a project from a specific employee
      @Query(value = "SELECT sum(total) totaal, p.project_name FROM timelog o INNER JOIN  task d ON o.task_id = d.task_id INNER JOIN  project p ON d.project_id = p.project_id where p.employee_id = :employeeId group by p.project_name;", nativeQuery = true)
      List<ITotalForProject> getTotalByProjectForEmployee(@Param("employeeId") UUID employeeId);

      // Get the total time for each project
      @Query(value = "SELECT sum(total) totaal, p.project_name FROM timelog o INNER JOIN  task d ON o.task_id = d.task_id INNER JOIN  project p ON d.project_id = p.project_id group by p.project_name;", nativeQuery = true)
      List<ITotalForProject> getTotalByProjects();

      // Get the average time for each task
      @Query(value = "select  avg(total) average , b.name  from Timelog o INNER JOIN task b ON b.task_id = o.task_id group by b.name " , nativeQuery = true)
      List<IAverageTask> getAverageTaskTime();

      // Get vacation timelogs from a certain employee between different dates

      @Query(value = "select  *  from Timelog o INNER JOIN timelog_cat tc ON tc.timelog_cat_id = o.timelog_cat_id where o.employee_id = :employee and tc.name='Day_off' and o.startam between :startDate and :endDate" , nativeQuery = true)
      List<Timelog> getEmployeeVacation(@Param("employee") UUID employee ,@Param("startDate") Date startDate ,@Param("endDate") Date endDate);

      // Get all timesheets for a specific project
      @Query(value = "SELECT *, p.project_name FROM timelog o INNER JOIN  task d ON o.task_id = d.task_id INNER JOIN  project p ON d.project_id = p.project_id where p.project_id = :projectId", nativeQuery = true)
      List<Timelog> getTimesheetForAProject(@Param("projectId") UUID projectId );

      // Get all timesheets from a specific employee thats between two dates
      @Query(value = "select * from Timelog t where t.employee_id = :employeeId and t.date between :startDate and :endDate" , nativeQuery = true)
      List<Timelog> findTimelogByEmployee(@Param("employeeId") UUID employeeId ,@Param("startDate") Date startDate ,@Param("endDate") Date endDate);

      // Get all timesheets for a specific task
      List<Timelog> findTimelogsByTaskTaskIdIn(List<UUID> ids);

      // Get all timesheets for a specific employee
      @Query(value = "select  *  from Timelog o INNER JOIN timelog_cat tc ON tc.timelog_cat_id = o.timelog_cat_id where o.employee_id = :employee and tc.name='Working'" , nativeQuery = true)
      List<Timelog> findTimelogsByEmployeeEmployeeId(@Param("employee") UUID employee);

      // Get all timesheets from a specific date
      List<Timelog> findTimelogsByTaskTaskIdInAndDate(List<UUID> ids, Date date);











}
