package ed.ap.stage.Tijdsregistratie.services;

import ed.ap.stage.Tijdsregistratie.exceptions.BadRequestException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T, ID> implements BaseService<T, ID> {

    protected abstract CrudRepository<T, ID> getRepository();

    @Override
    public T getById(ID id) {
        Optional<T> product = getRepository().findById(id);
        return product
                .orElseThrow(() -> new BadRequestException("Cannot find entity by id: " + id));

    }

    @Override
    public List<T> getAll() {
        return (List<T>) getRepository().findAll();
    }

}
