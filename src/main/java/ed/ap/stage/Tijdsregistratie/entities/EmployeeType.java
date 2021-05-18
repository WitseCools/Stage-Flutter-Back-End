package ed.ap.stage.Tijdsregistratie.entities;

import ed.ap.stage.Tijdsregistratie.enums.EmployeeRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@Table(name = "Employee_type")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeType {

    @Id
    @GeneratedValue
    private UUID employeeTypeId;


    private EmployeeRole employeeRole;


}
