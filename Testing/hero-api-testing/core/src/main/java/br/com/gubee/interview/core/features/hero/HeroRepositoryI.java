package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.base.repository.BaseRepository;
import br.com.gubee.interview.model.Hero;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroRepositoryI extends BaseRepository<Hero, UUID> {
    boolean alreadyExists(String heroName);

    List<Hero> retriveByName(String name);

}
