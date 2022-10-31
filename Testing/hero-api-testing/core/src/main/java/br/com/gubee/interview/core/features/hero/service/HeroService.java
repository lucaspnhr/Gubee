package br.com.gubee.interview.core.features.hero.service;

import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.RetrieveHeroRequest;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface HeroService {
    @Transactional
    UUID create(CreateHeroRequest createHeroRequest);

    @Transactional
    RetrieveHeroRequest retriveById(UUID id);

    @Transactional
    List<RetrieveHeroRequest> retriveByName(String name);

    @Transactional
    RetrieveHeroRequest update(UUID id, UpdateHeroRequest updateHeroRequest);

    @Transactional
    void deleteById(UUID id);

    @Transactional
    List<RetrieveHeroRequest> retriveHerosByIds(UUID firstHero, UUID secondHero);
}
