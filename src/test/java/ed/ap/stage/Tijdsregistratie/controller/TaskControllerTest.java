package ed.ap.stage.Tijdsregistratie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ed.ap.stage.Tijdsregistratie.dto.TaskDto;
import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.entities.Task;
import ed.ap.stage.Tijdsregistratie.repositories.EmployerRepository;
import ed.ap.stage.Tijdsregistratie.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {


    private MockMvc mockMvc;

    @MockBean
    private EmployerRepository employerRepository;

    @MockBean
    private TaskService service;

    @Autowired
    private WebApplicationContext context;

    private UUID projectId;
    private TaskDto taskDto;

    private UUID taskId;

    @BeforeEach
    public void setup(){

          taskId = UUID.fromString("58b29931-d7e3-45e1-8dc8-4dc84197bf75");

         projectId = UUID.fromString("58b29931-d7e3-45e1-8dc8-4dc84197bf76");

        final List<Task> tasks = new ArrayList<Task>();

        Project project = Project.builder().projectId(projectId).name("Project1").build();

        tasks.add(Task.builder().taskId(UUID.fromString("58b29931-d7e3-45e1-8dc8-4dc84197bf75")).name("Task").project(project).build());

        taskDto = TaskDto.builder().taskId(UUID.randomUUID()).name("a").build();

        when(service.getTasksByProject(projectId)).thenReturn(tasks);
        when(service.getById(taskId)).thenReturn(tasks.get(0));
        when(service.CreateTask(taskDto)).thenReturn(Task.builder()
                .taskId(UUID.fromString("58b29931-d7e3-45e1-8dc8-4dc84197bf77")).name(taskDto.getName()).build());

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void getAll()  throws Exception {
        this.mockMvc.perform(get("/api/tasks/").param("projectId",projectId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
        .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[*]", hasSize(1)))
        .andExpect(jsonPath("$[*].taskId", containsInAnyOrder("58b29931-d7e3-45e1-8dc8-4dc84197bf75")));


    }

    @Test
    void getById() throws Exception {
        this.mockMvc.perform(get("/api/tasks/58b29931-d7e3-45e1-8dc8-4dc84197bf75")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(3)))
                .andExpect(jsonPath("$.name", is("Task")));
    }

    @Test
    void addLocation() throws Exception{
        this.mockMvc.perform(post("/api/tasks/add")
                .content(asJsonString(this.taskDto))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", is("58b29931-d7e3-45e1-8dc8-4dc84197bf77")))
                .andExpect(jsonPath("$.name", is(this.taskDto.getName())));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}