package ed.ap.stage.Tijdsregistratie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ed.ap.stage.Tijdsregistratie.dto.EmployerDto;
import ed.ap.stage.Tijdsregistratie.dto.LocationDto;
import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.entities.Location;
import ed.ap.stage.Tijdsregistratie.entities.Project;
import ed.ap.stage.Tijdsregistratie.services.LocationService;
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
class LocationControllerTest {

    private MockMvc mockMvc;

    @MockBean
    LocationService locationService;

    @Autowired
    private WebApplicationContext context;


    Location location;
    Location location2;
    Location locationEmployer1;
    Location locationEmployer2;

    Project project;
    Employer employer;

    LocationDto locationDto;

    UUID locationId1 = UUID.fromString("d20fa812-b00c-11eb-8529-0242ac130003");
    UUID locationId2 = UUID.fromString("d20fa812-b00c-11eb-8529-0242ac130004");
    UUID employerId = UUID.fromString("d20fa812-b00c-11eb-8529-0242ac130005");
    UUID projectId = UUID.fromString("d20fa812-b00c-11eb-8529-0242ac130006");


    @BeforeEach
    void setup() {

        locationDto = LocationDto.builder().locationId(locationId1).locationName("Location1").locationLat(50.123456f).build();
        Date date = new Date(2021, 05, 12);
        employer = Employer.builder().name("Employer").employerId(employerId).date(date).build();
        project = Project.builder().name("Project").projectId(projectId).employer(employer).period(date).salary(20.0).build();
        location = Location.builder().locationId(locationId1).locationName("Boom").locationLat(50.123456f).locationLon(49.123456f).radius(20).build();
        location2 = Location.builder().locationId(locationId2).locationName("Niel").locationLat(50.123455f).locationLon(49.123455f).radius(25).build();


        locationEmployer1 = Location.builder().locationId(locationId1).locationName("Boom").locationLat(50.123456f).locationLon(49.123456f).radius(20).build();
        locationEmployer2 = Location.builder().locationId(locationId2).locationName("Niel").locationLat(50.123455f).locationLon(49.123455f).radius(25).build();

        final List<Location> locaties = new ArrayList<Location>();
        final List<Location> locatiesEmployer = new ArrayList<Location>();

        locatiesEmployer.add(locationEmployer1);
        locatiesEmployer.add(locationEmployer2);

        locaties.add(location);
        locaties.add(location2);

        when(locationService.getAll()).thenReturn(locaties);
        when(locationService.getById(location.getLocationId())).thenReturn(location);
        when(locationService.getByProjectId(employerId)).thenReturn(locatiesEmployer);
        when(locationService.CreateLocation(locationDto)).thenReturn(Location.builder().locationId(UUID.fromString("d20fa812-b00c-11eb-8529-0242ac130003")).locationName("Location1").locationLat(50.123456f).build());


        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void getAll() throws Exception {
        this.mockMvc.perform(get("/api/locations/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].locationId", is("d20fa812-b00c-11eb-8529-0242ac130003")))
                .andExpect(jsonPath("$[1].locationId", is("d20fa812-b00c-11eb-8529-0242ac130004")));
    }

    @Test
    void getById() throws Exception {
        this.mockMvc.perform(get("/api/locations/d20fa812-b00c-11eb-8529-0242ac130003")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(6)))
                .andExpect(jsonPath("$.locationName", is("Boom")))
                .andExpect(jsonPath("$.locationId", is("d20fa812-b00c-11eb-8529-0242ac130003")));

    }

    @Test
    void getByProject() throws Exception {
        this.mockMvc.perform(get("/api/locations/project/d20fa812-b00c-11eb-8529-0242ac130005")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].locationId", is("d20fa812-b00c-11eb-8529-0242ac130003")));


    }

    @Test
    void addLocation() throws Exception {
        this.mockMvc.perform(post("/api/locations/add")
                .content(asJsonString(this.locationDto))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.locationId", is("d20fa812-b00c-11eb-8529-0242ac130003")))
                .andExpect(jsonPath("$.locationName", is(this.locationDto.getLocationName())));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}