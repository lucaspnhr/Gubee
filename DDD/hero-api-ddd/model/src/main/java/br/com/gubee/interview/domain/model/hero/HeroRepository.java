package br.com.gubee.interview.domain.model.hero;

import java.util.List;
import java.util.Optional;

public interface HeroRepository {

    /**
    * Find hero with specified Id
    * @param heroId id
    * @return  {@code Optional<Hero>} if found or empty Optional
    * */
    Optional<Hero> find(HeroId heroId);
    /**
     * Find all heroes in repository
     * @return {@code List<Hero>} or empty list if none is found
     * */
    List<Hero> findAll();

    /**
     * Find any hero with name containing query
     * @return {@code List<Hero>} or empty list if none is found
     */
    List<Hero> findByName(String query);

    /**
     * Save hero in storage
     * @param hero entity
     * @return  HeroId
     * */
    HeroId save(Hero hero);


}
