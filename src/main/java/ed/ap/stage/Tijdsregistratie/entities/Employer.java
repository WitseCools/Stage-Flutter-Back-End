package ed.ap.stage.Tijdsregistratie.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "employer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID employerId;


    private String name;
    private Date date;


     // private Set<Project> projects; // mag mss weg


    // private Set<Location> locations; // mag mss weg

}
