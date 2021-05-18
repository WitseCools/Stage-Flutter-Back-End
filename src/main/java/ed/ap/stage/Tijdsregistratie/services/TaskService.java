package ed.ap.stage.Tijdsregistratie.services;


import ed.ap.stage.Tijdsregistratie.dto.ProjectDto;
import ed.ap.stage.Tijdsregistratie.dto.TaskDto;
import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.entities.Task;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TaskService extends BaseService<Task, UUID>  {

    List<Task> getTasksByProject(UUID id);

    Task CreateTask(String name, UUID projectId);
    default Task CreateTask(TaskDto task){
        return CreateTask(task.getName() , task.getProjectId());
    }
}
