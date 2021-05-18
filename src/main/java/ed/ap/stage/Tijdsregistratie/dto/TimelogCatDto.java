package ed.ap.stage.Tijdsregistratie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimelogCatDto {

    private UUID timelogCatId;
    private String name;

}
