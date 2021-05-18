package ed.ap.stage.Tijdsregistratie.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "timelog")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Timelog {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID timelogId;


    private Timestamp startAM;

    private Timestamp startPM;

    private Timestamp stopAM;

    private Timestamp stopPM;
    private Date date;


    private int total;



    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;


    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;


    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;


    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "timelog_cat_id")
    private TimelogCat timelogCat;




}
