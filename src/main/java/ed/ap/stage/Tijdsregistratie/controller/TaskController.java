package ed.ap.stage.Tijdsregistratie.controller;
import ed.ap.stage.Tijdsregistratie.dto.ProjectDto;
import ed.ap.stage.Tijdsregistratie.dto.TaskDto;
import ed.ap.stage.Tijdsregistratie.annotations.Dto;
import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.entities.Task;
import ed.ap.stage.Tijdsregistratie.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    @Dto(TaskDto.class)
    public List<Task> GetAll(@RequestParam UUID projectId){
            return taskService.getTasksByProject(projectId);
    }

    @GetMapping("/{id}")
    @Dto(TaskDto.class)
    public Task GetById(@PathVariable("id") UUID id){
        Task t = taskService.getById(id);
        return t;
    }

    @PostMapping(value ="/add" , consumes = "application/json")
    @Dto(TaskDto.class)
    public ResponseEntity<?> AddTask(@RequestBody TaskDto task) throws Exception{

        Task t = taskService.CreateTask(task);

        return ResponseEntity
                .created(
                        new URI("/api/tasks" + t.getTaskId())
                )
                .body(t);
    }



}
