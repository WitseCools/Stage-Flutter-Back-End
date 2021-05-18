package ed.ap.stage.Tijdsregistratie.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "location")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID locationId;

    @Column(name = "location_name")
    private String locationName;

    @Column(name ="location_lat")
    private float locationLat;

    @Column(name ="location_lon")
    private float locationLon;

    @Column(name ="location_radius")
    private int radius;


    //private Set<Timelog> timelogs;

    @ManyToOne(fetch =  FetchType.EAGER )
    @JoinColumn(name = "employer_id")
    private Employer employer;





}
