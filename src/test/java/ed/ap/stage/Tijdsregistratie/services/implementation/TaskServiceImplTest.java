package ed.ap.stage.Tijdsregistratie.services.implementation;

import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.entities.Task;
import ed.ap.stage.Tijdsregistratie.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {


    TaskServiceImpl taskService;

    @Mock
    ProjectServiceImpl projectService;

    @Mock
    TaskRepository taskRepository;
    UUID projectId; Project project;
    @BeforeEach
    void setup( ){
        taskService = new TaskServiceImpl(taskRepository,projectService);

        projectId = UUID.randomUUID();

        project = Project.builder().projectId(projectId).build();
        Mockito.when(projectService.getById(project.getProjectId())).thenReturn(project);

    }



    @Test
    void createTask() {

        taskService.CreateTask("Task", project.getProjectId());

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        Mockito.verify(taskRepository).save(captor.capture());
        Task task = captor.getValue();

        assertEquals("Task", task.getName());
        assertEquals(project, task.getProject());
    }
}