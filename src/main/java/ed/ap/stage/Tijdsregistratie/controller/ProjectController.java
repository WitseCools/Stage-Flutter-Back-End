package ed.ap.stage.Tijdsregistratie.controller;

import ed.ap.stage.Tijdsregistratie.dto.EmployerDto;
import ed.ap.stage.Tijdsregistratie.dto.ProjectDto;
import ed.ap.stage.Tijdsregistratie.annotations.Dto;
import ed.ap.stage.Tijdsregistratie.dto.TimelogDto;
import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.security.CurrentUser;
import ed.ap.stage.Tijdsregistratie.security.UserPrincipal;
import ed.ap.stage.Tijdsregistratie.services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
    @RequestMapping("/api/projects")
public class ProjectController {


    ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    @Dto(ProjectDto.class)
    public List<Project> getAll(@RequestParam(required = false) Optional<UUID> employerId , @RequestParam(required = false) Optional<UUID> employeeId  ){
        if( employerId.isPresent()){
            return service.getEmployerProjects(employerId.get());
        }
        if(employeeId.isPresent()){
            return service.getEmployeeProjects(employeeId.get());
        }
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Dto(ProjectDto.class)
    public Project getById(@PathVariable UUID id){
        Project d = service.getById(id);
        return d;
    }

    @GetMapping(value = "/my")
    @Dto(ProjectDto.class)
    public List<Project> getMyTasks(@CurrentUser UserPrincipal userPrincipal){
        return service.getEmployeeProjects(userPrincipal.getId());
    }

    @PostMapping("/add")
    @Dto(ProjectDto.class)
    public ResponseEntity<?> AddProject(@RequestBody ProjectDto project){

        Project t = service.CreateProject(project);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(t.getEmployer()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{id}")
    @Dto(ProjectDto.class)
    public ResponseEntity<?> updateProject(@PathVariable("id") UUID id, @RequestBody ProjectDto projectDto){
        service.Update(id, projectDto.getEmployeeId());
        return ResponseEntity.ok().build();
    }


}
