package ed.ap.stage.Tijdsregistratie.dto;

import ed.ap.stage.Tijdsregistratie.entities.Employer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private UUID locationId;

    private String locationName;

    private float locationLat;

    private float locationLon;

    private int radius;

    private UUID employerId;



}
