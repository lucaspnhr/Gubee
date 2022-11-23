package br.com.gubee.interview.core.application.out;

import br.com.gubee.interview.domain.model.hero.Hero;
import br.com.gubee.interview.domain.model.hero.HeroId;

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
     * Save hero in storage or update it if already in repository
     * @param hero entity
     * @return  HeroId
     * */
    HeroId save(Hero hero);

    /**
     * Delete hero from repository that matches given id
     * @param heroId
     * @return positive integer if hero succefully deleted or negative if not
     */
    int delete(HeroId heroId);

    /**
     *
     * @param name id
     * @return true if hero is present in repository or false if is not
     */
    boolean alreadyExists(String name);

}
