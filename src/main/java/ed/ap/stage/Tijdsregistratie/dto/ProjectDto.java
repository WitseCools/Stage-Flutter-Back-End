package ed.ap.stage.Tijdsregistratie.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import ed.ap.stage.Tijdsregistratie.entities.Employer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private UUID projectId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
    private Date period;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
    private Date startDate;
    private String name;
    private Double salary;
    private UUID employerId;
    private UUID employeeId;


}
