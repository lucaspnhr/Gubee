package br.com.gubee.interview.core.features.hero.repository;

import br.com.gubee.interview.core.features.shared.repository.BaseRepository;
import br.com.gubee.interview.domain.model.hero.Hero;

import java.util.List;
import java.util.UUID;

public interface HeroRepository extends BaseRepository<Hero, UUID> {
    boolean alreadyExists(String heroName);

    List<Hero> retriveByName(String name);

}
