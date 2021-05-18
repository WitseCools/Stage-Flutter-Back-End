package ed.ap.stage.Tijdsregistratie.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TimelogDto {



    private UUID timelogId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
    private Timestamp startAM;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
    private Timestamp startPM;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
    private Timestamp stopAM;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
    private Timestamp stopPM;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
    private Date date;

    private int total;


    private UUID locationId;



    private UUID taskId;


    private UUID employeeId;


    private UUID timeLogCatId;



}
