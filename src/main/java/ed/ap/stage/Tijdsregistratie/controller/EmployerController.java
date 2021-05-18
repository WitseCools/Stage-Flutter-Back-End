package ed.ap.stage.Tijdsregistratie.controller;

import ed.ap.stage.Tijdsregistratie.dto.EmployerDto;
import ed.ap.stage.Tijdsregistratie.annotations.Dto;
import ed.ap.stage.Tijdsregistratie.dto.TimelogDto;
import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.entities.Timelog;
import ed.ap.stage.Tijdsregistratie.services.EmployerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    EmployerService service;

    public EmployerController(EmployerService service) {
        this.service = service;
    }

    @GetMapping("/")
    @Dto(EmployerDto.class)
    public List<Employer> Index(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Dto(EmployerDto.class)
    public Employer GetById(@PathVariable("id") UUID id){
        Employer employer = service.getById(id);
        return employer;
    }

    @PostMapping(value = "/add" , consumes = "application/json")
    @Dto(EmployerDto.class)
    public ResponseEntity<?> addEmployer(@RequestBody EmployerDto employer) throws Exception{

        Employer t = service.CreateEmployer(employer);

        return ResponseEntity
                .created(
                        new URI("/api/employers" + t.getEmployerId())
                )
                .body(t);
    }

}
