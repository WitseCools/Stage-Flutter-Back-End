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
@Table(name = "timelog_cat")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimelogCat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID timelogCatId;
    private String name;



}
