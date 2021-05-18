package ed.ap.stage.Tijdsregistratie.repositories;

import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.entities.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    TaskRepository repository;

    @Autowired
    TestEntityManager manager;

    @Test
    void getTasksByProject_ProjectId() {
        Project project = Project.builder()
                .name("TestProject")
                .build();

        Task task = Task.builder()
                .name("Test")
                .project(project)
                .build();

        project = manager.persist(project);
        task = manager.persist(task);

        List<Task> tasks =  repository.getTasksByProject_ProjectId(project.getProjectId());

        assertEquals(tasks.size() , 1);
        assertNotEquals(tasks , false);
        assertNotEquals(tasks.get(0).getName() , "TestProject");
        assertNotEquals(task.getProject() , project.getProjectId());

    }
}