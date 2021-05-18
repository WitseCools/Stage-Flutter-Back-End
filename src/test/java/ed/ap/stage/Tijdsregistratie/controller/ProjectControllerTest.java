package ed.ap.stage.Tijdsregistratie.controller;

import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.services.LocationService;
import ed.ap.stage.Tijdsregistratie.services.ProjectService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {


    @MockBean
    ProjectService projectService;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    Project project1;
    Project project2;

    UUID projectId = UUID.randomUUID();
    UUID projectId2 = UUID.randomUUID();

    @BeforeEach
    void setup(){
        List<Project> projectList =new ArrayList<Project>();

        project1 = Project.builder().projectId(projectId).salary(20.0).name("Project").build();
        project2 = Project.builder().projectId(projectId2).salary(40.0).name("Project2").build();
        projectList.add(project1);
       // projectList.add(project2);

        when(projectService.getAll()).thenReturn(projectList);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void getAll() throws Exception{
        this.mockMvc.perform(get("/api/projects"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].projectId", containsInAnyOrder(projectId.toString())));
    }

    @Test
    void getById() throws Exception {
        this.mockMvc.perform(get("/api/contact/58b29931-d7e3-45e1-8dc8-4dc84197bf75"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("58b29931-d7e3-45e1-8dc8-4dc84197bf75")));
    }

    @Test
    void getMyTasks() throws Exception {
    }

    @Test
    void addProject() throws Exception {
    }

    @Test
    void updateProject() throws Exception {
    }
}