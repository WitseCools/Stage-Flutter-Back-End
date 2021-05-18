package ed.ap.stage.Tijdsregistratie.services.implementation;

import ed.ap.stage.Tijdsregistratie.entities.Task;
import ed.ap.stage.Tijdsregistratie.repositories.TaskRepository;
import ed.ap.stage.Tijdsregistratie.services.AbstractService;
import ed.ap.stage.Tijdsregistratie.services.ProjectService;
import ed.ap.stage.Tijdsregistratie.services.TaskService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl  extends AbstractService<Task, UUID> implements TaskService {

    TaskRepository repository;
    ProjectService projectService;

    public TaskServiceImpl(TaskRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Override
    protected CrudRepository<Task, UUID> getRepository() {
        return repository;
    }


    @Override
    public List<Task> getTasksByProject(UUID id) {
        return repository.getTasksByProject_ProjectId(id);
    }

    @Override
    public Task CreateTask(String name, UUID projectId) {
        Task task = Task.builder()
                .name(name)
                .project(projectService.getById(projectId))
                .build();

        return repository.save(task);
    }
}
