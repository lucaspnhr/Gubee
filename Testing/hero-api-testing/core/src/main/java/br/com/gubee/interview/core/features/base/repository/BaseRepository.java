package br.com.gubee.interview.core.features.base.repository;

import java.util.Optional;

public interface BaseRepository<T, ID> {


    ID create(T entity);

    Optional<T> retriveById(ID id);


    int update(T entityToUpdate);

    int delete(ID id);

    void deleteAll();
}
