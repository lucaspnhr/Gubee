package br.com.gubee.interview.core.features.hero.service;

import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.RetrieveHeroRequest;
import br.com.gubee.interview.model.request.UpdateHeroRequest;

import java.util.List;
import java.util.UUID;

public interface HeroService {
    UUID create(CreateHeroRequest createHeroRequest);

    RetrieveHeroRequest retriveById(UUID id);

    List<RetrieveHeroRequest> retriveByName(String name);

    RetrieveHeroRequest update(UUID id, UpdateHeroRequest updateHeroRequest);

    void deleteById(UUID id);

    List<RetrieveHeroRequest> retriveHerosByIds(UUID firstHero, UUID secondHero);

    void deleteAll();
}
