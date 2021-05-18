package ed.ap.stage.Tijdsregistratie.dto;

import ed.ap.stage.Tijdsregistratie.entities.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private UUID employeeId;
    private String firstName;
    private String lastName;
    private String function;
    private boolean active;
    private Set<EmployeeType> employee_type;

}
