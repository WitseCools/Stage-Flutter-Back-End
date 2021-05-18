package ed.ap.stage.Tijdsregistratie.controller;

import ed.ap.stage.Tijdsregistratie.dto.LocationDto;
import ed.ap.stage.Tijdsregistratie.annotations.Dto;
import ed.ap.stage.Tijdsregistratie.entities.Location;
import ed.ap.stage.Tijdsregistratie.services.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/")
    @Dto(LocationDto.class)
    public List<Location> GetAll(@RequestParam(required = false) Optional<UUID> employerId){
        if(employerId.isPresent()){
            return locationService.getLocationsByEmployer(employerId.get());
        }
        return locationService.getAll();
    }

    @GetMapping("/{id}")
    @Dto(LocationDto.class)
    public Location GetById(@PathVariable("id") UUID id){
        Location l = locationService.getById(id);
        return l;
    }



    @GetMapping("/project/{id}")
    @Dto(LocationDto.class)
    public List<Location> GetByProject(@PathVariable("id") UUID id){
        return locationService.getByProjectId(id);
    }

    @PostMapping(value = "/add" , consumes = "application/json" )
    @Dto(LocationDto.class)
    public ResponseEntity<?> addLocation(@RequestBody LocationDto location)throws Exception{

        Location t = locationService.CreateLocation(location);

        return ResponseEntity
                .created(
                        new URI("/api/locations" + t.getLocationId())
                )
                .body(t);
    }

}
