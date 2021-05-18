package ed.ap.stage.Tijdsregistratie.services.implementation;


import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.entities.Location;
import ed.ap.stage.Tijdsregistratie.repositories.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {


    LocationServiceImpl locationService;

    @Mock
    LocationRepository repository;

    @Mock
    EmployerServiceImpl employerServiceImpl;

    @Mock
    ProjectServiceImpl projectServiceImp;

    Employer employer;
    UUID randomUUid;

    @BeforeEach
    private void setup() {

        locationService = new LocationServiceImpl(repository,projectServiceImp,employerServiceImpl);
        Date date = new Date(2021, 05, 28);

        randomUUid = UUID.randomUUID();


        employer = Employer.builder().employerId(randomUUid).name("Employer").date(date).build();

        Mockito.when(employerServiceImpl.getById(employer.getEmployerId())).thenReturn(employer);
    }

    @Test
    void createLocation() {


        locationService.CreateLocation("Test",50.123456f,50.123456f,25,employer.getEmployerId());

        ArgumentCaptor<Location> captor = ArgumentCaptor.forClass(Location.class);
        Mockito.verify(repository).save(captor.capture());
        Location location = captor.getValue();

        assertEquals("Test", location.getLocationName());

    }
}