package ed.ap.stage.Tijdsregistratie.controller;

import ed.ap.stage.Tijdsregistratie.dto.TimelogCatDto;
import ed.ap.stage.Tijdsregistratie.annotations.Dto;
import ed.ap.stage.Tijdsregistratie.entities.TimelogCat;
import ed.ap.stage.Tijdsregistratie.services.TimeLogCatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet_cat")
public class TimeLogCatController {

    TimeLogCatService service;

    public TimeLogCatController(TimeLogCatService service) {
        this.service = service;
    }

    @GetMapping("/")
    @Dto(TimelogCatDto.class)
    public List<TimelogCat> Index(){
        return service.getAll();
    }

}
