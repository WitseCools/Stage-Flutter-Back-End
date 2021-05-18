package ed.ap.stage.Tijdsregistratie.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private String email;
    @Column(name = "pass")
    private String password;
    @Column(name = "func")
    private String function;
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="Employees_type_user" , joinColumns= @JoinColumn(name="employee_id"), inverseJoinColumns = @JoinColumn(name="employee_type_id"))
    private Set<EmployeeType> employee_type;

   /* @OneToMany(mappedBy = "employee")
    private Set<Project> projects;*/


   // private Set<Timelog> timelogs;


}
