package ed.ap.stage.Tijdsregistratie.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "project")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="project_id")
    private UUID projectId;
    @Column(name = "project_period")
    private Date period;
    @Column(name = "project_start_date")
    private Date startDate;
    @Column(name = "project_salary")
    private Double salary;
    @Column(name = "project_name")
    private String name;


    // private Set<Task> tasks;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
