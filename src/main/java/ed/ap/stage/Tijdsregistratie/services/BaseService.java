package ed.ap.stage.Tijdsregistratie.services;

import java.util.List;

public interface BaseService<T,ID> {

    T getById(ID id);
    List<T> getAll();
}
