package ed.ap.stage.Tijdsregistratie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ed.ap.stage.Tijdsregistratie.dto.EmployeeDto;
import ed.ap.stage.Tijdsregistratie.dto.EmployerDto;
import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.entities.Task;
import ed.ap.stage.Tijdsregistratie.services.EmployerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.util.Date;
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
class EmployerControllerTest {

    @MockBean
    EmployerService employerService;

    private Employer employer;
    private Employer employer2;
    private Date date = new Date(2021, 05, 12);
    private UUID employerId1 = UUID.fromString("bd22c094-b000-11eb-8529-0242ac130003");
    private UUID employerId2 = UUID.randomUUID();
    private MockMvc mockMvc;
    EmployerDto employerDto;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {

        employer = Employer.builder().employerId(employerId1).name("Employer").date(date).build();
        employer2 = Employer.builder().employerId(employerId2).name("Employer2").date(date).build();
        final List<Employer> employers = new ArrayList<Employer>();
        employers.add(employer2);
        employers.add(employer);

        employerDto = EmployerDto.builder().employerId(employerId1).date(date).name("Employer").build();

        when(employerService.getAll()).thenReturn(employers);
        when(employerService.getById(employer.getEmployerId())).thenReturn(employer);
        when(employerService.CreateEmployer(employerDto)).thenReturn(Employer.builder().employerId(UUID.fromString("bd22c094-b000-11eb-8529-0242ac130003")).name("Employer").build());

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void index() throws Exception {
        this.mockMvc.perform(get("/api/employers/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].employerId", is(employerId2.toString())))
                .andExpect(jsonPath("$[1].employerId", is(employerId1.toString())));
    }

    @Test
    void getById() throws Exception {
        this.mockMvc.perform(get("/api/employers/bd22c094-b000-11eb-8529-0242ac130003")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(3)))
                .andExpect(jsonPath("$.employerId", is(employerId1.toString())))
                .andExpect(jsonPath("$.name", is("Employer")));
    }

    @Test
    void addEmployer() throws Exception {
        this.mockMvc.perform(post("/api/employers/add")
                .content(asJsonString(this.employerDto))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employerId", is("bd22c094-b000-11eb-8529-0242ac130003")))
                .andExpect(jsonPath("$.name", is(this.employerDto.getName())));


    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}