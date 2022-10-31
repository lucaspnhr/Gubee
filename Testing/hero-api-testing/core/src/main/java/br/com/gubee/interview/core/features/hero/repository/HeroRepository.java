package br.com.gubee.interview.core.features.hero.repository;

import br.com.gubee.interview.core.features.base.repository.BaseRepository;
import br.com.gubee.interview.model.Hero;

import java.util.List;
import java.util.UUID;

public interface HeroRepository extends BaseRepository<Hero, UUID> {
    boolean alreadyExists(String heroName);

    List<Hero> retriveByName(String name);

}
