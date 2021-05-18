package ed.ap.stage.Tijdsregistratie.services.implementation;

import ed.ap.stage.Tijdsregistratie.entities.Employer;
import ed.ap.stage.Tijdsregistratie.entities.Location;
import ed.ap.stage.Tijdsregistratie.repositories.EmployerRepository;
import ed.ap.stage.Tijdsregistratie.repositories.LocationRepository;
import ed.ap.stage.Tijdsregistratie.services.EmployerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployerServiceImplTest {

    EmployerServiceImpl employerService;


    @Mock
    EmployerRepository employerRepository;
    Date date;



    @BeforeEach
    private void setup() {

        employerService = new EmployerServiceImpl(employerRepository);
         date = new Date(2021, 05, 28);

    }

    @Test
    void createEmployer() {
        employerService.CreateEmployer("Test",date);

        ArgumentCaptor<Employer> captor = ArgumentCaptor.forClass(Employer.class);
        Mockito.verify(employerRepository).save(captor.capture());
        Employer employer = captor.getValue();

        assertEquals("Test", employer.getName());
        assertEquals(date, employer.getDate());

    }
}