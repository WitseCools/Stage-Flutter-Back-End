package ed.ap.stage.Tijdsregistratie.services.implementation;

import ed.ap.stage.Tijdsregistratie.entities.TimelogCat;
import ed.ap.stage.Tijdsregistratie.repositories.TimeLogCatRepository;
import ed.ap.stage.Tijdsregistratie.services.AbstractService;
import ed.ap.stage.Tijdsregistratie.services.TimeLogCatService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TimeLogCatServiceImpl extends AbstractService<TimelogCat, UUID> implements TimeLogCatService {

    TimeLogCatRepository repository;

    @Override
    protected CrudRepository<TimelogCat, UUID> getRepository() {
        return repository;
    }

    public TimeLogCatServiceImpl(TimeLogCatRepository repository) {
        this.repository = repository;
    }


}
