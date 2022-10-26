package br.com.gubee.interview.core.features.base.repository;

import br.com.gubee.interview.model.Hero;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseRepository<T, ID> {


    ID create(T entity);

    Optional<T> retriveById(ID id);


    int update(T entityToUpdate);

    int delete(ID id);
}
